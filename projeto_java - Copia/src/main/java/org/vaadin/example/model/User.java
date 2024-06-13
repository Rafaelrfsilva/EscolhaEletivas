package org.vaadin.example.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String email;
    private String role; // "student" or "teacher"
    private String password; // adicionado o campo de senha
    private List<Eletiva> electiveCourses;

    public User(String email, String role,String password) { // ajustado o construtor para incluir a senha
        this.email = email;
        this.role = role;
        this.password = password;
        this.electiveCourses = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public List<Eletiva> getElectiveCourses() {
        return electiveCourses;
    }

    public String getPassword() { // adicionado o método getPassword para retornar a senha
        return password;
    }
}
