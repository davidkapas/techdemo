package com.david_kapas.android.techtest.presentation.details.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.annimon.stream.Stream;
import com.david_kapas.android.techtest.BR;
import com.david_kapas.android.techtest.logic.api.model.Comment;
import com.david_kapas.android.techtest.logic.api.model.PostDetails;
import com.david_kapas.android.techtest.presentation.common.util.ImageUtil;
import com.david_kapas.android.techtest.presentation.details.model.CommentListEntity;
import com.david_kapas.android.techtest.presentation.details.model.PostDetailsEntity;
import com.david_kapas.android.techtest.presentation.details.model.PostDetailsModel;
import com.david_kapas.android.techtest.presentation.details.router.PostDetailsRouter;

import java.util.List;

/**
 * ViewModel class for PostDetails.
 * Created by David Kapas on 2018.03.18.
 */

public class PostDetailsViewModel extends BaseObservable {

    private final PostDetailsModel model;
    private final PostDetailsRouter router;
    private PostDetails postDetails;
    private int postId;
    private int numberOfComments;
    private String avatarUrl;

    public PostDetailsViewModel(PostDetailsModel postDetailsModel, PostDetailsRouter router) {
        this.model = postDetailsModel;
        this.router = router;
        observeModel();
    }

    public void showPostDetails(int postId) {
        this.postId = postId;
        model.showPostOverview(postId);
        model.getComments();
    }

    @Bindable
    public PostDetails getPostDetails() {
        return postDetails;
    }

    @Bindable
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Bindable
    public int getNumberOfComments() {
        return numberOfComments;
    }

    private void onPostDetailsModel(CommentListEntity commentListEntity) {
        if (commentListEntity.isError()) {
            // the extended error handling not implemented due to short time frame
            router.showGeneralErrorDialog();
        } else {
            List<Comment> list = commentListEntity.getCommentList();
            setNumberOfComments(Stream.ofNullable(list).filter(comment -> comment.getPostId() == postId).toList().size());
        }

    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
        notifyPropertyChanged(BR.numberOfComments);
    }

    private void observeModel() {
        model.getPostDetailsEntityMutableLiveData().observe(router, this::onPostOverview);
        model.getCommentListEntityMutableLiveData().observe(router, this::onPostDetailsModel);
    }

    private void onPostOverview(PostDetailsEntity postDetailsEntity) {
        if (postDetailsEntity.isError()) {
            // the extended error handling not implemented due to short time frame
            router.showGeneralErrorDialog();
        } else {
            setPostDetails(postDetailsEntity);
        }
    }

    private void setPostDetails(PostDetailsEntity postDetailsEntity) {
        this.postDetails = postDetailsEntity.getPostDetails();
        avatarUrl = ImageUtil.createAvatarImageUrl(postDetails.getUserEmail());
        notifyPropertyChanged(BR.postDetails);
        notifyPropertyChanged(BR.avatarUrl);
    }
}
