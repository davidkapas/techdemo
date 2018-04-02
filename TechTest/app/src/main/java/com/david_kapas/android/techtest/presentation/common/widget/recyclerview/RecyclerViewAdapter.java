package com.david_kapas.android.techtest.presentation.common.widget.recyclerview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.david_kapas.android.techtest.BR;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Common adapter which is used for each recyclerview when using databinding.
 * Created by David Kapas on 2018.03.17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private int column;
    private List<ListItemViewModel> items = new ArrayList<>();
    private List<Integer> viewTypes;
    private List<Integer> itemLayouts;

    public RecyclerViewAdapter() {
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int itemLayout = itemLayouts.get(viewTypes != null ? viewTypes.indexOf(viewType) : viewType);
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), itemLayout, parent, false);
        return new BindingViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.viewModel, items.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = items == null ? 0 : items.size();
        if (itemCount > column) {
            itemCount = getRoundDownItemCount(itemCount);
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType();
    }

    public void setItems(List<ListItemViewModel> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    protected List<ListItemViewModel> getItems() {
        return items;
    }

    public void setItemLayout(int itemLayout) {
        this.itemLayouts = Collections.singletonList(itemLayout);
    }

    public void setViewTypes(List<Integer> viewTypes) {
        this.viewTypes = viewTypes;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    private int getRoundDownItemCount(int itemCount) {
        return itemCount - itemCount % column;
    }

}