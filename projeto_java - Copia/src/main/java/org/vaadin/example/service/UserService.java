package org.vaadin.example.service;

import org.vaadin.example.model.Eletiva;
import org.vaadin.example.model.User;
import org.vaadin.example.view.LoginView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private static UserService instance;
    private List<User> users;

    private UserService() {
        users = new ArrayList<>();
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void addUser(org.vaadin.example.view.LoginView.User user) {
        users.add(user);
    }

    public List<User> getAllUsers() {
        return users;
    }

    public List<User> getUnassignedStudents() {
        return users.stream()
                .filter(user -> "estudante".equals(user.getRole()) && user.getElectiveCourses().isEmpty())
                .collect(Collectors.toList());
    }

    public List<User> getProfessorsForCourse(Eletiva course) {
        return users.stream()
                .filter(user -> "professor".equals(user.getRole()) && user.getElectiveCourses().contains(course))
                .collect(Collectors.toList());
    }

    public User getUserByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserByEmail'");
    }
}
