////////////////////////////////////////////////////////////////////
// [TOMMASO] [POPPI] [1201270]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

import java.time.LocalDate;

public class User {
    private int id;
    private String name;
    private LocalDate data_nascita;

    public User(int num, String nome, LocalDate data) {
        this.id = num;
        this.name = nome;
        this.data_nascita = data;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return data_nascita;
    }
}