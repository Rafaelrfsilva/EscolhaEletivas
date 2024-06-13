package org.vaadin.example.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.example.model.Eletiva;
import org.vaadin.example.model.User;
import org.vaadin.example.service.ElectiveCourseService;
import org.vaadin.example.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Route("admin")
public class AdminView extends VerticalLayout {

    public AdminView() {
        // Tabela para exibir usuários
        Grid<User> userGrid = new Grid<>(User.class);
        userGrid.setColumns("email", "role");
        userGrid.setItems(UserService.getInstance().getAllUsers());

        // Tabela para exibir disciplinas
        Grid<Eletiva> courseGrid = new Grid<>(Eletiva.class);
        courseGrid.addColumn(Eletiva::getNome).setHeader("Eletiva");
        courseGrid.addColumn(Eletiva::getTema).setHeader("Ano");
        courseGrid.addColumn(Eletiva::getBibliografia).setHeader("Turma");
        courseGrid.addColumn(course -> ElectiveCourseService.getInstance().getStudentCount(course)).setHeader("Ocupação");
        courseGrid.addColumn(course -> {
            List<User> professors = UserService.getInstance().getProfessorsForCourse(course);
            return professors.stream().map(User::getEmail).collect(Collectors.joining(", "));
        }).setHeader("Professor");

        courseGrid.setItems(ElectiveCourseService.getInstance().getElectiveCourses());

        // Tabela para exibir alunos não matriculados
        Grid<User> unassignedStudentsGrid = new Grid<>(User.class);
        unassignedStudentsGrid.setColumns("email", "role");
        unassignedStudentsGrid.setItems(UserService.getInstance().getUnassignedStudents());

        // Botão para alocar alunos
        Button allocateButton = new Button("Allocate Selected Students", e -> {
            User selectedStudent = unassignedStudentsGrid.asSingleSelect().getValue();
            Eletiva selectedCourse = courseGrid.asSingleSelect().getValue();

            if (selectedStudent != null && selectedCourse != null) {
                ElectiveCourseService.getInstance().registerStudent("N/A", "N/A", selectedCourse, selectedStudent);
                Notification.show("Student allocated successfully!");
                unassignedStudentsGrid.setItems(UserService.getInstance().getUnassignedStudents());
                courseGrid.getDataProvider().refreshAll();
            } else {
                Notification.show("Please select a student and a course.", 3000, Notification.Position.MIDDLE);
            }
        });

        add(userGrid, courseGrid, unassignedStudentsGrid, allocateButton);
    }
}
