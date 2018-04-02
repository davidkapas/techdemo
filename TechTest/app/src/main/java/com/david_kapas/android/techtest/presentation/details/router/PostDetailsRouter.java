package com.david_kapas.android.techtest.presentation.details.router;

import android.arch.lifecycle.LifecycleOwner;

/**
 * PostDetails router interface for navigation from PostDetailsActivity.
 * Created by David Kapas on 2018.03.18.
 */

public interface PostDetailsRouter extends LifecycleOwner {

    void showGeneralErrorDialog();
}
