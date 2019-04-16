package com.david_kapas.android.techtest.di.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.david_kapas.android.techtest.di.scopes.PerActivity;
import com.david_kapas.android.techtest.logic.api.PostsApi;
import com.david_kapas.android.techtest.logic.api.UsersApi;
import com.david_kapas.android.techtest.logic.dao.PostDao;
import com.david_kapas.android.techtest.logic.dao.UserDao;
import com.david_kapas.android.techtest.presentation.posts.model.PostListModel;
import com.david_kapas.android.techtest.presentation.posts.viewmodel.PostListItemViewModel;
import com.david_kapas.android.techtest.presentation.posts.viewmodel.PostsViewModel;

import javax.inject.Provider;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for ListActivity.
 * Created by David Kapas on 2018.03.17.
 */
@Module
public class ListActivityModule {

    @Provides
    @PerActivity
    PostListModel providePostListModel(PostsApi postsApi, PostDao postDao, UsersApi usersApi, UserDao userDao) {
        return new PostListModel(postsApi, postDao, usersApi, userDao);
    }

    @Provides
    PostListItemViewModel providePostListItemViewModel() {
        return new PostListItemViewModel();
    }

    @Provides
    PostViewModelFactory providePostViewModelFactory(PostListModel postListModel, Provider<PostListItemViewModel> postListItemViewModelProvider) {
        return new PostViewModelFactory(postListModel, postListItemViewModelProvider);
    }

    public class PostViewModelFactory implements ViewModelProvider.Factory {

        private PostListModel postListModel;
        private Provider<PostListItemViewModel> postListItemViewModelProvider;

        public PostViewModelFactory(PostListModel postListModel, Provider<PostListItemViewModel> postListItemViewModelProvider) {
            this.postListModel = postListModel;
            this.postListItemViewModelProvider = postListItemViewModelProvider;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new PostsViewModel(postListModel, postListItemViewModelProvider);
        }
    }

}
