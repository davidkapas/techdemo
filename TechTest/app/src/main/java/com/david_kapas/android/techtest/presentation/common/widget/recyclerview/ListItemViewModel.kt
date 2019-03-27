package com.david_kapas.android.techtest.presentation.common.widget.recyclerview

import android.databinding.BaseObservable

/**
 * Abstract viewmodel for list items which provides the viewtype of the item.
 * Created by David Kapas on 2018.03.17.
 *
 */

abstract class ListItemViewModel : BaseObservable() {
    val FULL_GRID_SIZE = -1
    val DEFAULT_GRID_SIZE = 1

    var spanSize: Int = DEFAULT_GRID_SIZE;

    abstract fun getViewType(): Int;

    fun areItemsTheSame(newItem: ListItemViewModel): Boolean {
        return this.equals(newItem);
    }

    fun applyDiff(itemViewModel: ListItemViewModel): Boolean {
        return false;
    }
}