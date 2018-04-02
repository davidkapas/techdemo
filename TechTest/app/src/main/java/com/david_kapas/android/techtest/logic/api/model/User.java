package com.david_kapas.android.techtest.logic.api.model;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

/**
 * Pojo class for User object.
 * Created by David Kapas on 2018.03.18.
 */
@Entity(primaryKeys = {"id"})
public class User {

    @NonNull
    private int id;
    private String name;
    private String username;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
