package org.vaadin.example.view;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.vaadin.example.model.Eletiva;
import org.vaadin.example.model.ViewEletivaCurso;
import org.vaadin.example.service.ElectiveCourseService;

import java.util.List;

@Route("elective-course")
public class ElectiveCourseView extends VerticalLayout {

    public ElectiveCourseView() {
        TextField nomeCampo = new TextField("Eletiva");
        TextField temaCampo = new TextField("Tema");
        TextField bibliografiaCampo = new TextField("Bibliografia");

        Button createButton = new Button("Criar Eletiva", e -> {
            String nome = nomeCampo.getValue();
            String tema = temaCampo.getValue();
            String bibliografia = bibliografiaCampo.getValue();

            if (!nome.isEmpty() && !tema.isEmpty() && !bibliografia.isEmpty()) {
                Eletiva course = new Eletiva(nome, tema, bibliografia);
                ElectiveCourseService.getInstance().addElectiveCourse(course);

                Notification.show("Eletiva criada com sucesso");
                nomeCampo.clear();
                temaCampo.clear();
                bibliografiaCampo.clear();
            } else {
                Notification.show("Favor, preencher os campos.", 3000, Notification.Position.MIDDLE);
            }
        });



        // Center the form layout
        VerticalLayout formLayout = new VerticalLayout(nomeCampo, temaCampo, bibliografiaCampo, createButton);
        formLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        formLayout.setSizeUndefined();

        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(formLayout);

        List<Eletiva> electiveCourses = ElectiveCourseService.getInstance().getElectiveCourses();
        Grid<Eletiva> grid = new Grid<>(Eletiva.class);
        grid.setItems(electiveCourses);
        grid.addColumn(course -> ElectiveCourseService.getInstance().getStudentCount(course)).setHeader("Occupied Slots");

        add(grid);
    }
}
