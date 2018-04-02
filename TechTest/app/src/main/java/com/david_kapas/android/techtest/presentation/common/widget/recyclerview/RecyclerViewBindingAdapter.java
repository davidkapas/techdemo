package com.david_kapas.android.techtest.presentation.common.widget.recyclerview;

import android.databinding.BindingAdapter;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * BindingAdapter which defines custom binding for recyclerview.
 * Created by David Kapas on 2018.03.17.
 */

public class RecyclerViewBindingAdapter {

    private RecyclerViewBindingAdapter() {
    }

    @BindingAdapter("columns")
    public static void setupRecyclerViewColumns(RecyclerView recyclerView, int columns) {
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), columns));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getAdapter(recyclerView).setColumn(columns);
    }

    @BindingAdapter({"itemSpacing", "columns"})
    public static void setupRecyclerViewItemSpacing(RecyclerView recyclerView, float itemSpacing, int columns) {
        setupRecyclerViewColumns(recyclerView, columns);
        recyclerView.addItemDecoration(new SpacesItemDecorationForGridLayoutManager((int) itemSpacing, columns));
    }

    @BindingAdapter("source")
    public static void setupRecyclerViewSource(RecyclerView recyclerView, List<ListItemViewModel> source) {
        getAdapter(recyclerView).setItems(source);
    }

    @BindingAdapter("itemLayout")
    public static void setupRecyclerViewItemLayout(RecyclerView recyclerView, @LayoutRes int itemLayout) {
        getAdapter(recyclerView).setItemLayout(itemLayout);
    }

    private static RecyclerViewAdapter getAdapter(RecyclerView recyclerView) {
        RecyclerViewAdapter adapter = (RecyclerViewAdapter) recyclerView.getAdapter();
        if (adapter == null) {
            adapter = new RecyclerViewAdapter();
            recyclerView.setAdapter(adapter);
        }
        return adapter;
    }
}
