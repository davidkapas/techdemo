package com.david_kapas.android.techtest.presentation.posts.viewmodel

import android.databinding.Bindable
import com.david_kapas.android.techtest.logic.api.model.Post
import com.david_kapas.android.techtest.presentation.common.util.ImageUtil
import com.david_kapas.android.techtest.presentation.common.widget.recyclerview.ListItemViewModel

/**
 * ViewModel class for one post item in recyclerview.
 * Created by David Kapas on 2018.03.17.
 */

class PostListItemViewModel() : ListItemViewModel() {
    @get:Bindable
    var post: Post? = null
    private var email: String? = null
    private val avatarUrl: String? = null

    override fun getViewType(): Int {
        return 0
    }

    @Bindable
    fun getAvatarUrl(): String {
        return ImageUtil.createAvatarImageUrl(email!!)
    }

    fun setEmail(email: String) {
        this.email = email
    }
}
