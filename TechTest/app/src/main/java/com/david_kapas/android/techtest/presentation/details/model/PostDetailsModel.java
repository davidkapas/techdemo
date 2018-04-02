package com.david_kapas.android.techtest.presentation.details.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.david_kapas.android.techtest.logic.api.CommentsApi;
import com.david_kapas.android.techtest.logic.api.model.Comment;
import com.david_kapas.android.techtest.logic.api.model.Post;
import com.david_kapas.android.techtest.logic.api.model.PostDetails;
import com.david_kapas.android.techtest.logic.api.model.User;
import com.david_kapas.android.techtest.logic.dao.PostDao;
import com.david_kapas.android.techtest.logic.dao.UserDao;
import com.david_kapas.android.techtest.presentation.common.model.BaseModel;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Model class for post details.
 * Created by David Kapas on 2018.03.18.
 */

public class PostDetailsModel extends BaseModel {

    private CommentsApi commentsApi;
    private PostDao postDao;
    private UserDao userDao;

    private MutableLiveData<CommentListEntity> commentListEntityMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<PostDetailsEntity> postDetailsEntityMutableLiveData = new MutableLiveData<>();

    public PostDetailsModel(CommentsApi commentsApi, PostDao postDao, UserDao userDao) {
        this.commentsApi = commentsApi;
        this.postDao = postDao;
        this.userDao = userDao;
    }

    public void showPostOverview(int postId) {
        Maybe<Post> postSource = postDao.getPostById(postId).subscribeOn(Schedulers.io());
        Maybe<User> userSource = postSource
                .flatMap(post -> userDao.getUserById(post.getUserId()).subscribeOn(Schedulers.io()));
        Maybe.zip(postSource, userSource, (post, user) -> {
            PostDetails postDetails = new PostDetails();
            postDetails.setPostId(post.getId());
            postDetails.setTitle(post.getTitle());
            postDetails.setBody(post.getBody());
            postDetails.setUserName(user.getName());
            postDetails.setUserEmail(user.getEmail());
            return postDetails;
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(this::onPostAndUserData, this::onPostAndUserError);

    }

    public void getComments() {
        if (commentListEntityMutableLiveData.getValue() == null) {
            commentsApi.getComments().subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onResult, this::onError);
        }
    }

    public LiveData<CommentListEntity> getCommentListEntityMutableLiveData() {
        return commentListEntityMutableLiveData;
    }

    public LiveData<PostDetailsEntity> getPostDetailsEntityMutableLiveData() {
        return postDetailsEntityMutableLiveData;
    }

    private void onPostAndUserError(Throwable throwable) {
        PostDetailsEntity postDetailsEntity = new PostDetailsEntity();
        postDetailsEntity.setError(true);
        postDetailsEntityMutableLiveData.setValue(postDetailsEntity);

    }

    private void onPostAndUserData(PostDetails postDetails) {
        postDetailsEntityMutableLiveData.setValue(new PostDetailsEntity(postDetails));
    }

    private void onError(Throwable throwable) {
        CommentListEntity commentListEntity = new CommentListEntity();
        commentListEntity.setError(true);
        commentListEntityMutableLiveData.setValue(commentListEntity);
    }

    private void onResult(List<Comment> comments) {
        commentListEntityMutableLiveData.setValue(new CommentListEntity(comments));
    }
}
