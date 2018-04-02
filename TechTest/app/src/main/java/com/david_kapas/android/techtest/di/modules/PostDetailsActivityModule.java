package com.david_kapas.android.techtest.di.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

import com.david_kapas.android.techtest.di.scopes.PerActivity;
import com.david_kapas.android.techtest.logic.api.CommentsApi;

import com.david_kapas.android.techtest.logic.dao.PostDao;
import com.david_kapas.android.techtest.logic.dao.UserDao;
import com.david_kapas.android.techtest.presentation.details.model.PostDetailsModel;
import com.david_kapas.android.techtest.presentation.details.router.PostDetailsActivity;
import com.david_kapas.android.techtest.presentation.details.router.PostDetailsRouter;
import com.david_kapas.android.techtest.presentation.details.viewmodel.PostDetailsViewModel;


import java.lang.ref.WeakReference;


import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for PostDetailActivity.
 * Created by David Kapas on 2018.03.18.
 */
@Module
public class PostDetailsActivityModule {

    private final WeakReference<PostDetailsActivity> activity;

    public PostDetailsActivityModule(PostDetailsActivity postDetailsActivity) {
        this.activity = new WeakReference<>(postDetailsActivity);
    }

    @Provides
    @PerActivity
    PostDetailsRouter providePostDetailsRouter() {
        return activity.get();
    }

    @Provides
    @PerActivity
    PostDetailsModel providePostDetailsModel(CommentsApi commentsApi, PostDao postDao, UserDao userDao) {
        final ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new PostDetailsModel(commentsApi, postDao, userDao);
            }
        };
        return ViewModelProviders.of(activity.get(), factory).get(PostDetailsModel.class);
    }

    @Provides
    @PerActivity
    PostDetailsViewModel providePostsViewModel(PostDetailsModel postDetailsModel, PostDetailsRouter router) {
        return new PostDetailsViewModel(postDetailsModel, router);
    }


}
