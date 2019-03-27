package com.david_kapas.android.techtest.presentation.posts.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import com.annimon.stream.Stream
import com.david_kapas.android.techtest.logic.api.model.PostAndUser
import com.david_kapas.android.techtest.presentation.common.extension.nonNull
import com.david_kapas.android.techtest.presentation.common.extension.observe
import com.david_kapas.android.techtest.presentation.common.widget.recyclerview.ListItemViewModel
import com.david_kapas.android.techtest.presentation.posts.model.PostAndUserEntity
import com.david_kapas.android.techtest.presentation.posts.model.PostListModel
import com.david_kapas.android.techtest.presentation.posts.router.PostListRouter
import javax.inject.Provider


/**
 * ViewModel part of the MVVM for the posts.
 * Created by David Kapas on 3/14/2019.
 */
class PostsViewModel(val model: PostListModel, val router: PostListRouter, val postListItemViewModelProvider: Provider<PostListItemViewModel>) : BaseObservable() {
    init {
        observeModel();
    }

    @get: Bindable
    var postList: List<ListItemViewModel> = ArrayList()
        set(value) {
            field = value
            notifyPropertyChanged(BR.postList)
        }


    fun getAllPosts() {
        model.getPosts()
    }

    private fun observeModel() {
        model.postAndUserEntityMutableLiveData.nonNull().observe(router, this::onPostListModel);

    }

    private fun onPostListModel(postAndUserEntity: PostAndUserEntity) {
        if (postAndUserEntity.isError) {
            // the extended error handling not implemented due to short time frame
            router.showGeneralErrorDialog()
        } else {
            postList = (toItemViewModels(postAndUserEntity.postAndUsers!!))
        }
    }

    private fun toItemViewModels(postAndUsers: List<PostAndUser>): List<ListItemViewModel> {
        return Stream.of(postAndUsers).map { postAndUser ->
            val itemViewModel: PostListItemViewModel = postListItemViewModelProvider.get()
            itemViewModel.post = postAndUser.post
            itemViewModel.setEmail(postAndUser.user.email ?: "")
            itemViewModel as ListItemViewModel
        }.toList()
    }
}