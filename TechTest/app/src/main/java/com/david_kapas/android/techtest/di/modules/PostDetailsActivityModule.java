package com.david_kapas.android.techtest.di.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.david_kapas.android.techtest.di.scopes.PerActivity;
import com.david_kapas.android.techtest.logic.api.CommentsApi;
import com.david_kapas.android.techtest.logic.dao.PostDao;
import com.david_kapas.android.techtest.logic.dao.UserDao;
import com.david_kapas.android.techtest.presentation.details.model.PostDetailsModel;
import com.david_kapas.android.techtest.presentation.details.viewmodel.PostDetailsViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for PostDetailActivity.
 * Created by David Kapas on 2018.03.18.
 */
@Module
public class PostDetailsActivityModule {

    @Provides
    @PerActivity
    PostDetailsModel providePostDetailsModel(CommentsApi commentsApi, PostDao postDao, UserDao userDao) {
        return new PostDetailsModel(commentsApi, postDao, userDao);
    }

    @Provides
    @PerActivity
    PostDetailsViewModelFactory providePostDetailsViewModelFactory(PostDetailsModel postDetailsModel) {
        return new PostDetailsViewModelFactory(postDetailsModel);
    }

    public class PostDetailsViewModelFactory implements ViewModelProvider.Factory {

        private PostDetailsModel postDetailsModel;

        public PostDetailsViewModelFactory(PostDetailsModel postDetailsModel) {
            this.postDetailsModel = postDetailsModel;

        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new PostDetailsViewModel(postDetailsModel);
        }
    }

}
