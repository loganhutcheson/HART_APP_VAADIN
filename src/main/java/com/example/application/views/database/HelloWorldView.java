package com.example.application.views.database;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.grid.Grid;
import com.example.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import com.example.application.PetService;
import com.example.application.Pet;
import com.example.application.PetForm;

@Route(value = "hello", layout = MainView.class)
@PageTitle("Database")
@CssImport("./styles/views/database/hello-world-view.css")
@RouteAlias(value = "", layout = MainView.class)
public class HelloWorldView extends VerticalLayout {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private PetService service = PetService.getInstance();
    private Grid<Pet> grid = new Grid<>(Pet.class);
    private TextField filterText = new TextField();
    private PetForm form = new PetForm(this);


    public HelloWorldView() {
        setId("hello-world-view");
        grid.setColumns("name", "status");
        add(filterText, grid, form);
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());
        updateList();
        form.setPet(null);
        grid.asSingleSelect().addValueChangeListener(event ->
            form.setPet(grid.asSingleSelect().getValue()));
    }

    public void updateList() {
        grid.setItems(service.findAll(filterText.getValue()));
    }

}
