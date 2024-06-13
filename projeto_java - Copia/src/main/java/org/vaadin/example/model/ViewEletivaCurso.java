package org.vaadin.example.model;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.example.model.Eletiva;
import org.vaadin.example.service.ElectiveCourseService;

import java.util.List;

@Route("view-eletivas")
public class ViewEletivaCurso extends VerticalLayout {

    public ViewEletivaCurso() {
        List<Eletiva> electiveCourses = ElectiveCourseService.getInstance().getElectiveCourses();

        Grid<Eletiva> grid = new Grid<>(Eletiva.class);
        grid.setItems(electiveCourses);
        grid.setColumns("nome", "turma", "vagas");

        add(grid);
    }
}
