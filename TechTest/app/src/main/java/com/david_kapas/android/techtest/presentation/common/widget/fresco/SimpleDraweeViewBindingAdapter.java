package com.david_kapas.android.techtest.presentation.common.widget.fresco;

import android.databinding.BindingAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Binding Adapter for any recyclerview item.
 * Created by David Kapas on 2018.03.17.
 */

public class SimpleDraweeViewBindingAdapter {

    private SimpleDraweeViewBindingAdapter() {
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(SimpleDraweeView view, String imageUrl) {
        view.setImageURI(imageUrl);
    }
}
