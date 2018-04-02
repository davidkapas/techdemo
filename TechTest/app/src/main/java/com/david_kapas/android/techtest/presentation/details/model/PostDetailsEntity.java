package com.david_kapas.android.techtest.presentation.details.model;

import com.david_kapas.android.techtest.logic.api.model.PostDetails;


/**
 * Wrapper class for PostDetails.
 * Created by David Kapas on 2018.03.18.
 */

public class PostDetailsEntity {

    private PostDetails postDetails;
    private boolean error;

    public PostDetailsEntity() {
    }

    public PostDetailsEntity(PostDetails postDetails) {
        this.postDetails = postDetails;
    }

    public PostDetails getPostDetails() {
        return postDetails;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
