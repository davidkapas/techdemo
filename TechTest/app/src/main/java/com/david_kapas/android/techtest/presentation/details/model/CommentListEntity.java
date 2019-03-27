package com.david_kapas.android.techtest.presentation.details.model;

import com.david_kapas.android.techtest.logic.api.model.Comment;

import java.util.List;

/**
 * Wrapper class for Posts.
 * Created by David Kapas on 2018.03.18.
 */

public class CommentListEntity {

    private List<Comment> commentList;
    private boolean error;

    public CommentListEntity() {
    }

    public CommentListEntity(List<Comment> comments) {
        this.commentList = comments;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
