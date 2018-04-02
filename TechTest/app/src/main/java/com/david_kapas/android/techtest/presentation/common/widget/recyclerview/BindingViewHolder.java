package com.david_kapas.android.techtest.presentation.common.widget.recyclerview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * General viewholder class for recyclerview items when using databinding.
 * Created by David Kapas on 2018.03.17.
 */

public class BindingViewHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;

    public BindingViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ViewDataBinding getBinding() {
        return binding;
    }
}