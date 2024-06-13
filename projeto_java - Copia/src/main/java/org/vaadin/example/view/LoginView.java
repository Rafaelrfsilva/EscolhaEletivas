package org.vaadin.example.view;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.example.model.StudentRegistrationView;

@Route("")
public class LoginView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_PASSWORD = "senha1234";
    private static Map<String, User> users = new HashMap<>();

    public LoginView() {
        setSizeFull();
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        showEmailStep();
    }

    private void showEmailStep() {
        EmailField emailField = new EmailField("Email");
        emailField.setPlaceholder("Digite seu e-mail");

        H1 title = new H1("SelectEdu");
        title.getStyle().set("color", "purple").set("font-size", "3em").set("text-align", "center");

        Button nextButton = new Button("Acessar", e -> {
            String email = emailField.getValue();
            if (validateEmail(email)) {
                if (users.containsKey(email)) {
                    showPasswordStep(email, users.get(email).getRole());
                } else {
                    users.put(email, new User(DEFAULT_PASSWORD, null, null));
                    Notification.show("Sua senha temporária é: " + DEFAULT_PASSWORD, 5000, Notification.Position.TOP_CENTER);
                    showPasswordStep(email, null);
                }
            } else {
                Notification.show("E-mail inválido. Por favor, use um endereço Gmail.", 3000, Notification.Position.TOP_CENTER);
            }
        });

        nextButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        nextButton.addClickShortcut(Key.ENTER);

        VerticalLayout emailLayout = new VerticalLayout(title, emailField, nextButton);
        emailLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        removeAll();
        add(emailLayout);
    }

    private void showPasswordStep(String email, String existingRole) {
        PasswordField passwordField = new PasswordField("Senha");

        Button nextButton = new Button("Próximo", e -> {
            String password = passwordField.getValue();
            if (existingRole != null) {
                if (password.equals(users.get(email).getPassword())) {
                    redirectToRolePage(existingRole);
                } else {
                    Notification.show("Senha inválida.", 3000, Notification.Position.TOP_CENTER);
                }
            } else if (password.equals(DEFAULT_PASSWORD)) {
                showNewPasswordStep(email);
            } else {
                Notification.show("Senha temporária inválida.", 3000, Notification.Position.TOP_CENTER);
            }
        });

        nextButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        nextButton.addClickShortcut(Key.ENTER);

        VerticalLayout passwordLayout = new VerticalLayout(passwordField, nextButton);
        passwordLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        removeAll();
        add(passwordLayout);
    }

    private void showNewPasswordStep(String email) {
        TextField nameField = new TextField("Nome");
        PasswordField newPasswordField = new PasswordField("Nova Senha");
        ComboBox<String> roleComboBox = new ComboBox<>("Selecione o Papel");
        roleComboBox.setItems("Estudante", "Professor", "Admin");

        Button loginButton = new Button("Login", e -> {
            String name = nameField.getValue();
            String newPassword = newPasswordField.getValue();
            String selectedRole = roleComboBox.getValue();

            if (name.isEmpty() || newPassword.isEmpty() || selectedRole == null) {
                Notification.show("Por favor, insira seu nome, uma nova senha e selecione um papel.", 3000, Notification.Position.TOP_CENTER);
            } else {
                users.put(email, new User(newPassword, selectedRole, name));
                Notification.show("Registro bem-sucedido! Redirecionando...", 3000, Notification.Position.MIDDLE);
                redirectToRolePage(selectedRole);
            }
        });

        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        VerticalLayout newPasswordLayout = new VerticalLayout(nameField, newPasswordField, roleComboBox, loginButton);
        newPasswordLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        removeAll();
        add(newPasswordLayout);
    }

    private void redirectToRolePage(String role) {
        switch (role) {
            case "Estudante":
                getUI().ifPresent(ui -> ui.navigate(StudentView.class));
                break;
            case "Professor":
                getUI().ifPresent(ui -> ui.navigate(TeacherView.class));
                break;
            case "Admin":
                getUI().ifPresent(ui -> ui.navigate(AdminView.class));
                break;
        }
    }

    private boolean validateEmail(String email) {
        return email.endsWith("@gmail.com");
    }

    private static class User {
        private final String password;
        private final String role;
        private final String name;

        public User(String password, String role, String name) {
            this.password = password;
            this.role = role;
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }

        public String getName() {
            return name;
        }
    }
}

@Route("estudante")
class StudentView extends VerticalLayout {
    private static final long serialVersionUID = 1L;

    public StudentView() {
        add(new H1("Bem-vindo, Aluno!"));
        Button logoutButton = new Button("Sair", e -> getUI().ifPresent(ui -> ui.navigate(LoginView.class)));
        Button AreaStudent = new Button("Formulário", e -> getUI().ifPresent(ui -> ui.navigate(StudentRegistrationView.class)));
        logoutButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(AreaStudent);
        add(logoutButton);
    }
}

@Route("professor")
class TeacherView extends VerticalLayout {
    public TeacherView() {
        add(new H1("Bem-vindo, Professor!"));
        Button logoutButton = new Button("Sair", e -> getUI().ifPresent(ui -> ui.navigate(LoginView.class)));
        Button AreaProf = new Button("Área de trabalho", e -> getUI().ifPresent(ui -> ui.navigate(ElectiveCourseView.class)));
        logoutButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(AreaProf);
        add(logoutButton);
    }
}

@Route("admin")
class AdminView extends VerticalLayout {
    public AdminView() {
        add(new H1("Bem-vindo, Administrador!"));
        Button logoutButton = new Button("Sair", e -> getUI().ifPresent(ui -> ui.navigate(LoginView.class)));
        Button AreaAdmin = new Button("Área de trabalho", e -> getUI().ifPresent(ui -> ui.navigate(AdminView.class)));
        logoutButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(AreaAdmin);
        add(logoutButton);
    }
}
