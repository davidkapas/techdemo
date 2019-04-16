package com.david_kapas.android.techtest.presentation.posts.viewmodel

import android.databinding.Bindable
import com.aidanvii.toolbox.databinding.ObservableArchViewModel
import com.android.databinding.library.baseAdapters.BR
import com.annimon.stream.Stream
import com.david_kapas.android.techtest.logic.api.model.PostAndUser
import com.david_kapas.android.techtest.logic.liveevent.SingleLiveEvent
import com.david_kapas.android.techtest.presentation.common.widget.recyclerview.ListItemViewModel
import com.david_kapas.android.techtest.presentation.common.widget.recyclerview.ListItemViewModelOnClick
import com.david_kapas.android.techtest.presentation.posts.model.PostListModel
import javax.inject.Provider


/**
 * ViewModel part of the MVVM for the posts.
 * Created by David Kapas on 3/14/2019.
 */
class PostsViewModel(val model: PostListModel, val postListItemViewModelProvider: Provider<PostListItemViewModel>) : ObservableArchViewModel(), ListItemViewModelOnClick {

    val showErrorMessage = SingleLiveEvent<Any>()
    val openDetails = SingleLiveEvent<Any>()

    @get: Bindable
    var postList: List<ListItemViewModel> = ArrayList()
        set(value) {
            field = value
            notifyPropertyChanged(BR.postList)
        }

    init {
        observeModel();
    }

    fun getAllPosts() {
        model.getPosts()
    }

    private fun observeModel() {
        if (postList.size == 0) {
            model.getPosts()?.subscribe({ this.onResult(it) }, { this.onError(it) });
        }
    }

    private fun onResult(postAndUsers: List<PostAndUser>) {
        postList = (toItemViewModels(postAndUsers))
    }

    private fun onError(throwable: Throwable) {
        showErrorMessage.call();
    }

    override fun onClick(itemViewModel: ListItemViewModel) {
        var postListItemViewModel = itemViewModel as PostListItemViewModel
        openDetails.value = postListItemViewModel.post?.id;
        openDetails.call()

    }

    private fun toItemViewModels(postAndUsers: List<PostAndUser>): List<ListItemViewModel> {
        return Stream.of(postAndUsers).map { postAndUser ->
            val itemViewModel: PostListItemViewModel = postListItemViewModelProvider.get()
            itemViewModel.post = postAndUser.post
            itemViewModel.listItemViewModelOnClick = this;
            itemViewModel.setEmail(postAndUser.user.email ?: "")
            itemViewModel as ListItemViewModel
        }.toList()
    }
}