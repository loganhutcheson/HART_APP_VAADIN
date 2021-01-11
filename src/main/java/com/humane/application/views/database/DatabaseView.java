package com.humane.application.views.database;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.grid.Grid;
import com.humane.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import com.humane.application.AnimalService;
import com.humane.application.Animal;
import com.humane.application.AnimalForm;

@Route(value = "hello", layout = MainView.class)
@PageTitle("Database")
@CssImport("./styles/views/database/database-view.css")
@RouteAlias(value = "", layout = MainView.class)
public class DatabaseView extends VerticalLayout {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private AnimalService service = AnimalService.getInstance();
    private Grid<Animal> grid = new Grid<>(Animal.class);
    private TextField filterText = new TextField();
    private AnimalForm form = new AnimalForm(this);


    public DatabaseView() {
        setId("database-view");
        grid.setColumns("name", "status");
        add(filterText, grid, form);
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());
        updateList();
        form.setAnimal(null);
        grid.asSingleSelect().addValueChangeListener(event ->
            form.setAnimal(grid.asSingleSelect().getValue()));
    }

    public void updateList() {
        grid.setItems(service.findAll(filterText.getValue()));
    }

}
