package com.david_kapas.android.techtest.presentation.posts.viewmodel;

import android.databinding.Bindable;
import android.view.View;

import com.david_kapas.android.techtest.logic.api.model.Post;
import com.david_kapas.android.techtest.presentation.common.util.ImageUtil;
import com.david_kapas.android.techtest.presentation.common.widget.recyclerview.ListItemViewModel;
import com.david_kapas.android.techtest.presentation.posts.router.PostListRouter;

/**
 * ViewModel class for one post item in recyclerview.
 * Created by David Kapas on 2018.03.17.
 */

public class PostListItemViewModel extends ListItemViewModel {
    private PostListRouter router;
    private Post post;
    private String email;
    private String avatarUrl;

    public PostListItemViewModel(PostListRouter router) {
        this.router = router;
    }

    @Override
    public int getViewType() {
        return 0;
    }

    public void onClick(View v) {
        router.openPost(post.getId());
    }

    @Bindable
    public Post getPost() {
        return post;
    }

    @Bindable
    public String getAvatarUrl() {
        return ImageUtil.createAvatarImageUrl(email);
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
