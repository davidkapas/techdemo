package com.david_kapas.android.techtest.presentation.posts.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.annimon.stream.Stream;
import com.david_kapas.android.techtest.BR;
import com.david_kapas.android.techtest.logic.api.model.PostAndUser;
import com.david_kapas.android.techtest.presentation.common.widget.recyclerview.ListItemViewModel;
import com.david_kapas.android.techtest.presentation.posts.model.PostAndUserEntity;
import com.david_kapas.android.techtest.presentation.posts.model.PostListModel;
import com.david_kapas.android.techtest.presentation.posts.router.PostListRouter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Provider;

/**
 * ViewModel part of the MVVM for the posts.
 * Created by David Kapas on 2018.03.17.
 */

public class PostsViewModel extends BaseObservable {

    private final PostListModel model;
    private final Provider<PostListItemViewModel> postListItemViewModelProvider;
    private final PostListRouter router;
    private List<ListItemViewModel> postList = new ArrayList<>();

    public PostsViewModel(PostListModel model, PostListRouter router, Provider<PostListItemViewModel> postListItemViewModelProvider) {
        this.model = model;
        this.router = router;
        this.postListItemViewModelProvider = postListItemViewModelProvider;
        observeModel();
    }

    private void observeModel() {
        model.getPostAndUserEntityMutableLiveData().observe(router, this::onPostListModel);

    }

    private void onPostListModel(PostAndUserEntity postAndUserEntity) {
        if (postAndUserEntity.isError()) {
            // the extended error handling not implemented due to short time frame
            router.showGeneralErrorDialog();
        } else {
            setPostList(toItemViewModels(postAndUserEntity.getPostAndUsers()));
        }
    }

    public void getAllPosts() {
        model.getPosts();
    }

    private List<ListItemViewModel> toItemViewModels(List<PostAndUser> postAndUsers) {
        return Stream.of(postAndUsers).map(postAndUser -> {
            PostListItemViewModel itemViewModel = postListItemViewModelProvider.get();
            itemViewModel.setPost(postAndUser.getPost());
            itemViewModel.setEmail(postAndUser.getUser().getEmail());
            return (ListItemViewModel) itemViewModel;
        }).toList();
    }

    @Bindable
    public List<ListItemViewModel> getPostList() {
        return postList;
    }

    public void setPostList(List<ListItemViewModel> list) {
        this.postList = list;
        notifyPropertyChanged(BR.postList);
    }
}
