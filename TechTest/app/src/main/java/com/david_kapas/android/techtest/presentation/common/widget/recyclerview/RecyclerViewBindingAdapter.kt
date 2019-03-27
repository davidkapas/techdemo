package com.david_kapas.android.techtest.presentation.common.widget.recyclerview

import android.databinding.BindingAdapter
import android.support.annotation.LayoutRes
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * BindingAdapter which defines custom binding for recyclerview.
 * Created by David Kapas on 3/12/2019.
 */

@BindingAdapter("columns")
fun setupRecyclerViewColumns(recyclerView: RecyclerView, columns: Int) {
    recyclerView.layoutManager = GridLayoutManager(recyclerView.context, columns);
    recyclerView.itemAnimator = DefaultItemAnimator();
    getAdapter(recyclerView).column = columns;
}

@BindingAdapter("itemSpacing", "columns")
fun setupRecyclerViewItemSpacing(recyclerView: RecyclerView, itemSpacing: Float, columns: Int) {
    setupRecyclerViewColumns(recyclerView, columns);
    recyclerView.addItemDecoration(SpacesItemDecorationForGridLayoutManager(itemSpacing.toInt(), columns))
}

@BindingAdapter("source")
fun setupRecyclerViewSource(recyclerView: RecyclerView, source: List<ListItemViewModel>) {
    getAdapter(recyclerView).setItems(source);
}

@BindingAdapter("itemLayout")
fun setupRecyclerViewItemLayout(recyclerView: RecyclerView, @LayoutRes itemLayout: Int) {
    getAdapter(recyclerView).setItemLayout(itemLayout);
}

fun getAdapter(recyclerView: RecyclerView): RecyclerViewAdapter {
    var adapter: RecyclerViewAdapter? = recyclerView.adapter as RecyclerViewAdapter?
    if (adapter == null) {
        adapter = RecyclerViewAdapter();
        recyclerView.adapter = adapter;
    }
    return adapter;
}