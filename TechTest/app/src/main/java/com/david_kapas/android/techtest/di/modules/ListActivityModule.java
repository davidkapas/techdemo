package com.david_kapas.android.techtest.di.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

import com.david_kapas.android.techtest.di.scopes.PerActivity;
import com.david_kapas.android.techtest.logic.api.PostsApi;
import com.david_kapas.android.techtest.logic.api.UsersApi;
import com.david_kapas.android.techtest.logic.dao.PostDao;
import com.david_kapas.android.techtest.logic.dao.UserDao;
import com.david_kapas.android.techtest.presentation.posts.model.PostListModel;
import com.david_kapas.android.techtest.presentation.posts.router.ListActivity;
import com.david_kapas.android.techtest.presentation.posts.router.PostListRouter;
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
    PostListRouter providePostListRouter(ListActivity listActivity) {
        return listActivity;
    }

    @Provides
    @PerActivity
    PostListModel providePostListModel(ListActivity listActivity, ValamiFactory valamiFactory) {
        return ViewModelProviders.of(listActivity, valamiFactory).get(PostListModel.class);
    }

    @Provides
    PostListItemViewModel providePostListItemViewModel(PostListRouter router) {
        return new PostListItemViewModel(router);
    }

    @Provides
    ValamiFactory provideValamiFactory(PostsApi postsApi, PostDao postDao, UsersApi usersApi, UserDao userDao) {
        return new ValamiFactory(postsApi, postDao, usersApi, userDao);
    }

    @Provides
    @PerActivity
    PostsViewModel providePostsViewModel(PostListModel postListModel, PostListRouter router,
            Provider<PostListItemViewModel> postListItemViewModelProvider) {
        return new PostsViewModel(postListModel, router, postListItemViewModelProvider);
    }

    public class ValamiFactory implements ViewModelProvider.Factory {

        private PostsApi postsApi;
        private PostDao postDao;
        private UsersApi usersApi;
        private UserDao userDao;

        public ValamiFactory(PostsApi postsApi, PostDao postDao, UsersApi usersApi, UserDao userDao) {
            this.postsApi = postsApi;
            this.postDao = postDao;
            this.usersApi = usersApi;
            this.userDao = userDao;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new PostListModel(postsApi, postDao, usersApi, userDao);
        }
    }

}
