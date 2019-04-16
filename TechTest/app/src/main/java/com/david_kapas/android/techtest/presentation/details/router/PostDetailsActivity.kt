package com.david_kapas.android.techtest.presentation.details.router

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.david_kapas.android.techtest.R
import com.david_kapas.android.techtest.databinding.ActivityPostDetailsBinding
import com.david_kapas.android.techtest.di.modules.PostDetailsActivityModule
import com.david_kapas.android.techtest.presentation.details.viewmodel.PostDetailsViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Main screen for showing post details.
 * Created by David Kapas on 3/26/2019.
 */
class PostDetailsActivity : AppCompatActivity() {


    companion object STATIC {
        public val POST_ID_PARAM = "postIdParam"
    }

    @Inject
    lateinit var postDetailsViewModelFactory: PostDetailsActivityModule.PostDetailsViewModelFactory;
    lateinit var postDetailsViewModel: PostDetailsViewModel;

    private var postId: Int = 0
    val alertDialog: AlertDialog by lazy { createAlertDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityPostDetailsBinding>(this, R.layout.activity_post_details)
        postDetailsViewModel = ViewModelProviders.of(this, postDetailsViewModelFactory).get(PostDetailsViewModel::class.java);
        binding.viewModel = postDetailsViewModel;
        postDetailsViewModel.showErrorMessage.observe(this, Observer { showGeneralErrorDialog() })
        if (intent != null) {
            postId = intent.getIntExtra(POST_ID_PARAM, -1)
        }
        if (postId > 0) {
            postDetailsViewModel.showPostDetails(postId)
        }

    }


    fun showGeneralErrorDialog() {
        showNetworkErrorDialog();
    }

    private fun createAlertDialog(): AlertDialog {
        val alertDialog = AlertDialog.Builder(this@PostDetailsActivity).create()
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