package org.vaadin.example.model;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.example.model.Eletiva;
import org.vaadin.example.service.ElectiveCourseService;

import java.util.List;

@Route("estudante-registro")
public class StudentRegistrationView extends VerticalLayout {

    public StudentRegistrationView() {
        ComboBox<String> yearComboBox = new ComboBox<>("Ano");
        yearComboBox.setItems("1 ANO", "2 ANO", "3 ANO");

        ComboBox<String> classComboBox = new ComboBox<>("Turma");
        classComboBox.setItems("A", "B", "C", "D");

        ComboBox<Eletiva> electiveComboBox1 = new ComboBox<>("Eletiva 1");
        ComboBox<Eletiva> electiveComboBox2 = new ComboBox<>("Eletiva 2");
        
        electiveComboBox1.setItemLabelGenerator(Eletiva::getNome);
        electiveComboBox2.setItemLabelGenerator(Eletiva::getNome);
        
        electiveComboBox2.setVisible(false); // Initially hide the second elective field

        yearComboBox.addValueChangeListener(event -> {
            String year = event.getValue();
            List<Eletiva> electiveCourses = ElectiveCourseService.getInstance().getElectiveCourses();
            if ("1 ANO".equals(year) || "3 ANO".equals(year)) {
                electiveComboBox1.setItems(electiveCourses.subList(0, Math.min(electiveCourses.size(), 4)));
                electiveComboBox2.setVisible(false);
            } else if ("2 ANO".equals(year)) {
                electiveComboBox1.setItems(electiveCourses.subList(0, Math.min(electiveCourses.size(), 8)));
                electiveComboBox2.setItems(electiveCourses.subList(0, Math.min(electiveCourses.size(), 8)));
                electiveComboBox2.setVisible(true);
            }
        });

        Button registerButton = new Button("Register", e -> {
            String year = yearComboBox.getValue();
            String studentClass = classComboBox.getValue();
            Eletiva elective1 = electiveComboBox1.getValue();
            Eletiva elective2 = electiveComboBox2.getValue();

            if (year == null || studentClass == null || elective1 == null || ("2 ANO".equals(year) && elective2 == null)) {
                Notification.show("Please fill in all required fields.", 3000, Notification.Position.MIDDLE);
            } else {
                ElectiveCourseService.getInstance().registerStudent(year, studentClass, elective1, null);
                if ("2 ANO".equals(year)) {
                    ElectiveCourseService.getInstance().registerStudent(year, studentClass, elective2, null);
                }
                Notification.show("Student registered successfully!", 3000, Notification.Position.MIDDLE);
                yearComboBox.clear();
                classComboBox.clear();
                electiveComboBox1.clear();
                electiveComboBox2.clear();
            }
        });

        add(yearComboBox, classComboBox, electiveComboBox1, electiveComboBox2, registerButton);
    }
}
