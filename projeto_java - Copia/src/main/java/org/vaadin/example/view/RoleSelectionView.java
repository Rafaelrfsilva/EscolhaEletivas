package org.vaadin.example.view;

import org.vaadin.example.model.StudentRegistrationView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("role-selection")
public class RoleSelectionView extends VerticalLayout {

    public RoleSelectionView() {
        Button studentButton = new Button("Estudante", event -> {
            getUI().ifPresent(ui -> ui.navigate(StudentRegistrationView.class));
        });
        Button teacherButton = new Button("Professor", event -> {
            getUI().ifPresent(ui -> ui.navigate(ElectiveCourseView.class));
        });
        Button adminButton = new Button("Admin", event -> {
            getUI().ifPresent(ui -> ui.navigate(AdminView.class));
        });

        studentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        teacherButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        adminButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        VerticalLayout buttonLayout = new VerticalLayout(studentButton, teacherButton, adminButton);
        buttonLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        buttonLayout.setSizeUndefined();

        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(buttonLayout);
    }
}
