package com.david_kapas.android.techtest.presentation.posts.router

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.david_kapas.android.techtest.R
import com.david_kapas.android.techtest.databinding.ActivityListBinding
import com.david_kapas.android.techtest.di.modules.ListActivityModule
import com.david_kapas.android.techtest.presentation.common.util.NetworkUtil
import com.david_kapas.android.techtest.presentation.details.router.PostDetailsActivity
import com.david_kapas.android.techtest.presentation.posts.viewmodel.PostsViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Main screen for showing posts.
 * Created by David Kapas on 3/27/2019.
 */
class ListActivity : AppCompatActivity() {

    lateinit var postsViewModel: PostsViewModel
    @Inject
    lateinit var postsViewModelFactory: ListActivityModule.PostViewModelFactory
    lateinit var binding: ActivityListBinding;
    val alertDialog: AlertDialog by lazy { createAlertDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        postsViewModel = ViewModelProviders.of(this, postsViewModelFactory).get(PostsViewModel::class.java);
        binding = DataBindingUtil.setContentView<ActivityListBinding>(this, R.layout.activity_list)
        binding.viewModel = postsViewModel
        postsViewModel.showErrorMessage.observe(this, Observer { showGeneralErrorDialog() })
        postsViewModel.openDetails.observe(this, Observer { openPost(it) })
        if (NetworkUtil.checkNetworkConnection()) {
            postsViewModel.getAllPosts()
        } else {
            showGeneralErrorDialog()
        }
        //TODO: PullToRefresh not implemented due to short time frame
    }

    override fun onDestroy() {
        alertDialog.dismiss()
        super.onDestroy()

    }

    fun showGeneralErrorDialog() {
        //TODO: extended error handling not implemented due to short time frame
        showNetworkErrorDialog()
    }

    fun openPost(it: Any?) {
        if (it != null) {
            var postId = it as Int
            val intent = Intent(this, PostDetailsActivity::class.java)
            intent.putExtra(PostDetailsActivity.POST_ID_PARAM, postId)
            startActivity(intent)
        }
    }

    private fun createAlertDialog(): AlertDialog {
        val alertDialog = AlertDialog.Builder(this@ListActivity).create()
        alertDialog.setTitle(R.string.network_error_title)
        alertDialog.setMessage(getString(R.string.network_error_message))
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { dialog, which -> dialog.dismiss() }
        return alertDialog;
    }

    private fun showNetworkErrorDialog() {
        alertDialog.show()
    }
}