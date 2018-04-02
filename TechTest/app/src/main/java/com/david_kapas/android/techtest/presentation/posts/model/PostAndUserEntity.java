package com.david_kapas.android.techtest.presentation.posts.model;

import com.david_kapas.android.techtest.logic.api.model.PostAndUser;

import java.util.List;

/**
 * Wrapper class for PostAndUser list.
 * Created by David Kapas on 2018.03.18.
 */

public class PostAndUserEntity {

    private List<PostAndUser> postAndUsers;
    private boolean error;

    public PostAndUserEntity() {
    }

    public PostAndUserEntity(List<PostAndUser> postAndUsers) {
        this.postAndUsers = postAndUsers;
    }

    public List<PostAndUser> getPostAndUsers() {
        return postAndUsers;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
