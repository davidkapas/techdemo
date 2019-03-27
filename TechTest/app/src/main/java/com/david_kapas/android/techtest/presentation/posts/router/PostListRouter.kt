package com.david_kapas.android.techtest.presentation.posts.router

import android.arch.lifecycle.LifecycleOwner

/**
 * PostList router interface for navigation from ListActivity.
 * Created by David Kapas on 2018.03.17.
 */

interface PostListRouter : LifecycleOwner {

    fun showGeneralErrorDialog()

    fun openPost(postId: Int)
}