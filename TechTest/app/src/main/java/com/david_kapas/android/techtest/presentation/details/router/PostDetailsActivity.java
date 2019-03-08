package com.david_kapas.android.techtest.presentation.details.router;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.david_kapas.android.techtest.R;
import com.david_kapas.android.techtest.databinding.ActivityPostDetailsBinding;
import com.david_kapas.android.techtest.presentation.details.viewmodel.PostDetailsViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * Main screen for showing post details.
 * Created by David Kapas on 2018.03.17.
 */

public class PostDetailsActivity extends AppCompatActivity implements PostDetailsRouter {

    public static final String POST_ID_PARAM = "postIdParam";

    @Inject
    PostDetailsViewModel postDetailsViewModel;

    private int postId;
    private boolean injected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        //inject();
        ActivityPostDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_post_details);
        binding.setViewModel(postDetailsViewModel);
        if (getIntent() != null) {
            postId = getIntent().getIntExtra(POST_ID_PARAM, -1);
        }
        if (postId > 0) {
            postDetailsViewModel.showPostDetails(postId);
        }

    }

    /*  private void inject() {
        if (!injected) {
            PostDetailsActivityComponent.Injector.buildComponent(this).inject(this);
            injected = true;
        }
    }*/

    @Override
    public void showGeneralErrorDialog() {
        showNetworkErrorDialog();
    }

    private void showNetworkErrorDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(PostDetailsActivity.this).create();
        alertDialog.setTitle(R.string.network_error_title);
        alertDialog.setMessage(getString(R.string.network_error_message));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }
}
