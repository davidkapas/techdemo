package com.david_kapas.android.techtest.logic.api.model;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

/**
 * Pojo class Post object.
 * Created by David Kapas on 2018.03.17.
 */
@Entity(primaryKeys = {"id"})
public class Post {

    private int userId;
    @NonNull
    private int id;
    private String title;
    private String body;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
