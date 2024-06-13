package org.vaadin.example.model;

public class Eletiva {
    private String nome;
    private String turma;
    private String dia;

    public Eletiva(String nome, String turma, String dia) {
        this.nome = nome;
        this.turma = turma;
        this.dia = dia;
    }

    public String getNome() {
        return nome;
    }

    public String getTema() {
        return turma;
    }

    public String getBibliografia() {
        return dia;
    }
}
