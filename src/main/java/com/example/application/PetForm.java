package com.example.application;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.example.application.views.database.HelloWorldView;
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

public class PetForm extends FormLayout {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private TextField name = new TextField("Name");
    private ComboBox<PetStatus> status = new ComboBox<>("Status");
    private DatePicker birthDate = new DatePicker("Birthdate");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button download = new Button("Download");

    private Binder<Pet> binder = new Binder<>(Pet.class);
    private HelloWorldView helloWorldView;
    private PetService service = PetService.getInstance();
    private FileDownloadWrapper buttonWrapper;


    public PetForm(HelloWorldView helloWorldView) {
        this.helloWorldView = helloWorldView;
        status.setItems(PetStatus.values());
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        
        HorizontalLayout buttons = new HorizontalLayout(save, delete, download);
        // Notification Inside Button
        Span content = new Span("Do you want to download?");
        NativeButton buttonConfirm = new NativeButton("Yes");
        NativeButton buttonClose = new NativeButton("No");
        Notification notification = new Notification(content, buttonClose);
        notification.setDuration(3000);
        buttonClose.addClickListener(event -> notification.close());
        notification.setPosition(Position.MIDDLE);
        download.addClickListener(event -> download());
        download.addClickListener(event -> notification.open());
        // Wrapper that sends pdf file
        try {
            byte[] petPdf = CreatePetPDF.createPDF(new Pet()).toByteArray();
            buttonWrapper = new FileDownloadWrapper(
               new StreamResource("error.pdf", () -> new ByteArrayInputStream(petPdf)));
            buttonWrapper.wrapComponent(buttonConfirm);
            notification.add(buttonWrapper);
            //buttons.add(buttonWrapper);
            //download.addClickListener(event-> download(buttonWrapper));
        } catch(IOException e) {
            e.printStackTrace();
        }
        add(name, status, birthDate, buttons);
        binder.bindInstanceFields(this);
    }

    public void setPet(Pet pet) {
        binder.setBean(pet);
        if (pet == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
        }
    }

    private void save() {
        Pet pet = binder.getBean();
        service.save(pet);
        helloWorldView.updateList();
        setPet(null);
    }

    private void delete() {
        Pet pet = binder.getBean();
        service.delete(pet);
        helloWorldView.updateList();
        setPet(null);
    }

    private void download() {
        Pet pet = binder.getBean();
        try {
            byte[] pdf = CreatePetPDF.createPDF(pet).toByteArray();
            buttonWrapper.setResource(new StreamResource(pet.getName()+".pdf", () -> new ByteArrayInputStream(pdf)));
           // FileDownloadWrapper link = new FileDownloadWrapper("pet.pdf", () -> pdf);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


}
