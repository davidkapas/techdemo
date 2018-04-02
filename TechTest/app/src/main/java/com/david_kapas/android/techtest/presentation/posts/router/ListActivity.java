package com.david_kapas.android.techtest.presentation.posts.router;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.david_kapas.android.techtest.R;
import com.david_kapas.android.techtest.databinding.ActivityListBinding;
import com.david_kapas.android.techtest.di.components.ListActivityComponent;
import com.david_kapas.android.techtest.presentation.common.util.NetworkUtil;
import com.david_kapas.android.techtest.presentation.details.router.PostDetailsActivity;
import com.david_kapas.android.techtest.presentation.posts.viewmodel.PostsViewModel;

import javax.inject.Inject;

/**
 * Main screen for showing posts.
 * Created by David Kapas on 2018.03.17.
 */

public class ListActivity extends AppCompatActivity implements PostListRouter{

    @Inject
    PostsViewModel postsViewModel;
    private boolean injected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        ActivityListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        binding.setViewModel(postsViewModel);
        if (NetworkUtil.checkNetworkConnection()) {
            postsViewModel.getAllPosts(false);
        } else {
            showGeneralErrorDialog();
        }

    }

    private void inject() {
        if (!injected) {
            ListActivityComponent.Injector.buildComponent(this).inject(this);
            injected = true;
        }
    }

    @Override
    public void showGeneralErrorDialog() {
        //TODO: extended error handling not implemented due to short time frame
        showNetworkErrorDialog();
    }

    @Override
    public void openPost(int postId) {
        Intent intent = new Intent(this, PostDetailsActivity.class);
        intent.putExtra(PostDetailsActivity.POST_ID_PARAM, postId);
        startActivity(intent);
    }

    private void showNetworkErrorDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(ListActivity.this).create();
        alertDialog.setTitle(R.string.network_error_title);
        alertDialog.setMessage(getString(R.string.network_error_message));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

}
