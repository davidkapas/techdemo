package com.david_kapas.android.techtest.logic.api.model;

/**
 * Pojo for holding Post and User object.
 * Created by David Kapas on 2018.03.18.
 */

public class PostAndUser {

    private Post post;
    private User user;

    public PostAndUser(Post post, User user) {
        this.post = post;
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public User getUser() {
        return user;
    }
}
