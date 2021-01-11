package com.humane.application;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.humane.application.views.database.DatabaseView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.server.StreamResource;

import org.vaadin.olli.FileDownloadWrapper;

public class AnimalForm extends FormLayout {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private TextField name = new TextField("Name");
    private ComboBox<AnimalStatus> status = new ComboBox<>("Status");
    private DatePicker birthDate = new DatePicker("Birthdate");
    private ComboBox<DogBreed> breed = new ComboBox<>("Breed");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button download = new Button("Download");

    private Binder<Animal> binder = new Binder<>(Animal.class);
    private DatabaseView databaseView;
    private AnimalService service = AnimalService.getInstance();
    private FileDownloadWrapper buttonWrapper;


    public AnimalForm(DatabaseView databaseView) {
        this.databaseView = databaseView;
        status.setItems(AnimalStatus.values());
        breed.setItems(DogBreed.values());
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        
        HorizontalLayout buttons = new HorizontalLayout(save, delete, download);
        // Notification Inside Button
        Span content = new Span("Do you want to download?");
        NativeButton buttonConfirm = new NativeButton("Yes");
        NativeButton buttonClose = new NativeButton("No");
        Notification notification = new Notification(content);
        notification.setDuration(3000);
        buttonClose.addClickListener(event -> notification.close());
        notification.setPosition(Position.MIDDLE);
        download.addClickListener(event -> download());
        download.addClickListener(event -> notification.open());
        // Wrapper that sends pdf file
        try {
            byte[] animalPdf = CreateRecordPDF.createPDF(new Animal()).toByteArray();
            buttonWrapper = new FileDownloadWrapper(
               new StreamResource("error.pdf", () -> new ByteArrayInputStream(animalPdf)));
            buttonWrapper.wrapComponent(buttonConfirm);
            notification.add(buttonWrapper, buttonClose);
            //buttons.add(buttonWrapper);
            //download.addClickListener(event-> download(buttonWrapper));
        } catch(IOException e) {
            e.printStackTrace();
        }
        add(name, status, birthDate, breed, buttons);
        binder.bindInstanceFields(this);
    }

    public void setAnimal(Animal animal) {
        binder.setBean(animal);
        if (animal == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
        }
    }

    private void save() {
        Animal animal = binder.getBean();
        service.save(animal);
        databaseView.updateList();
        setAnimal(null);
    }

    private void delete() {
        Animal animal = binder.getBean();
        service.delete(animal);
        databaseView.updateList();
        setAnimal(null);
    }

    private void download() {
        Animal animal = binder.getBean();
        try {
            byte[] pdf = CreateRecordPDF.createPDF(animal).toByteArray();
            buttonWrapper.setResource(new StreamResource(animal.getName()+".pdf", () -> new ByteArrayInputStream(pdf)));
           // FileDownloadWrapper link = new FileDownloadWrapper("animal.pdf", () -> pdf);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


}
