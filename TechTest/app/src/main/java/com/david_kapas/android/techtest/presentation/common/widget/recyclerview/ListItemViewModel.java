package com.david_kapas.android.techtest.presentation.common.widget.recyclerview;

import android.databinding.BaseObservable;

/**
 * Abstract viewmodel for list items which provides the viewtype of the item.
 * Created by David Kapas on 2018.03.17.
 *
 * @param <T> Type of the class.
 */

public abstract class ListItemViewModel<T> extends BaseObservable {

    public static final int FULL_GRID_SIZE = -1;
    public static final int DEFAULT_GRID_SIZE = 1;

    private int spanSize = DEFAULT_GRID_SIZE;

    public abstract int getViewType();

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }


    public boolean areItemsTheSame(ListItemViewModel newItem) {
        return this.equals(newItem);
    }

    public boolean applyDiff(ListItemViewModel itemViewModel) {
        return false;
    }
}
