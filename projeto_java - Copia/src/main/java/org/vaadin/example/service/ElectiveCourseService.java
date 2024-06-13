package org.vaadin.example.service;

import org.vaadin.example.model.Eletiva;
import org.vaadin.example.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElectiveCourseService {

    private static ElectiveCourseService instance;
    private List<Eletiva> electiveCourses;
    private Map<Eletiva, List<User>> studentRegistrations;

    private ElectiveCourseService() {
        electiveCourses = new ArrayList<>();
        studentRegistrations = new HashMap<>();
    }

    public static synchronized ElectiveCourseService getInstance() {
        if (instance == null) {
            instance = new ElectiveCourseService();
        }
        return instance;
    }

    public void addElectiveCourse(Eletiva course) {
        electiveCourses.add(course);
        studentRegistrations.put(course, new ArrayList<>());
    }

    public List<Eletiva> getElectiveCourses() {
        return electiveCourses;
    }

    public void registerStudent(String year, String studentClass, Eletiva course, User student) {
        List<User> students = studentRegistrations.get(course);
        if (students == null) {
            students = new ArrayList<>();
            studentRegistrations.put(course, students);
        }
        students.add(student);
        student.getElectiveCourses().add(course);
    }

    public int getStudentCount(Eletiva course) {
        return studentRegistrations.getOrDefault(course, new ArrayList<>()).size();
    }
}
