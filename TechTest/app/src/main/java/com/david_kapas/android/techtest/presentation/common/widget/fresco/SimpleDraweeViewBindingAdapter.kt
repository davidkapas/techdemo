package com.david_kapas.android.techtest.presentation.common.widget.fresco

import android.databinding.BindingAdapter

import com.facebook.drawee.view.SimpleDraweeView

/**
 * Binding Adapter for any recyclerview item.
 * Created by David Kapas on 2018.03.17.
 */

@BindingAdapter("imageUrl")
fun loadImage(view: SimpleDraweeView, imageUrl: String?) {
    view.setImageURI(imageUrl)
}
