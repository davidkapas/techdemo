package com.david_kapas.android.techtest.presentation.details.router

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.david_kapas.android.techtest.R
import com.david_kapas.android.techtest.databinding.ActivityPostDetailsBinding
import com.david_kapas.android.techtest.presentation.details.viewmodel.PostDetailsViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Main screen for showing post details.
 * Created by David Kapas on 3/26/2019.
 */
class PostDetailsActivity : AppCompatActivity(), PostDetailsRouter {


    companion object STATIC {
        public val POST_ID_PARAM = "postIdParam"
    }

    @Inject
    lateinit var postDetailsViewModel: PostDetailsViewModel;

    private var postId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityPostDetailsBinding>(this, R.layout.activity_post_details)
        binding.viewModel = postDetailsViewModel
        if (intent != null) {
            postId = intent.getIntExtra(POST_ID_PARAM, -1)
        }
        if (postId > 0) {
            postDetailsViewModel.showPostDetails(postId)
        }

    }


    override fun showGeneralErrorDialog() {
        showNetworkErrorDialog();
    }

    private fun showNetworkErrorDialog() {
        val alertDialog = AlertDialog.Builder(this@PostDetailsActivity).create()
        alertDialog.setTitle(R.string.network_error_title)
        alertDialog.setMessage(getString(R.string.network_error_message))
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { dialog, which -> dialog.dismiss() }
        alertDialog.show();
    }
}