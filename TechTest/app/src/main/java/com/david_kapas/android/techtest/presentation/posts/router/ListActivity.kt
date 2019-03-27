package com.david_kapas.android.techtest.presentation.posts.router

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.david_kapas.android.techtest.R
import com.david_kapas.android.techtest.databinding.ActivityListBinding
import com.david_kapas.android.techtest.presentation.common.util.NetworkUtil
import com.david_kapas.android.techtest.presentation.details.router.PostDetailsActivity
import com.david_kapas.android.techtest.presentation.posts.viewmodel.PostsViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Main screen for showing posts.
 * Created by David Kapas on 3/27/2019.
 */
class ListActivity : AppCompatActivity(), PostListRouter {

    @Inject
    lateinit var postsViewModel: PostsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityListBinding>(this, R.layout.activity_list)
        binding.viewModel = postsViewModel
        if (NetworkUtil.checkNetworkConnection()) {
            postsViewModel.getAllPosts()
        } else {
            showGeneralErrorDialog()
        }
        //TODO: PullToRefresh not implemented due to short time frame
    }

    override fun showGeneralErrorDialog() {
        //TODO: extended error handling not implemented due to short time frame
        showNetworkErrorDialog()
    }

    override fun openPost(postId: Int) {
        val intent = Intent(this, PostDetailsActivity::class.java)
        intent.putExtra(PostDetailsActivity.POST_ID_PARAM, postId)
        startActivity(intent)
    }

    private fun showNetworkErrorDialog() {
        val alertDialog = AlertDialog.Builder(this@ListActivity).create()
        alertDialog.setTitle(R.string.network_error_title)
        alertDialog.setMessage(getString(R.string.network_error_message))
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { dialog, which -> dialog.dismiss() }
        alertDialog.show()
    }
}