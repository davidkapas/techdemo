package com.david_kapas.android.techtest.presentation.common.widget.recyclerview

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

/**
 * General viewholder class for recyclerview items when using databinding.
 * Created by David Kapas on 3/13/2019.
 */

class BindingViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)