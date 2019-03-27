package com.david_kapas.android.techtest.presentation.common.widget.recyclerview

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.david_kapas.android.techtest.BR
import java.util.*

/**
 * Common adapter which is used for each recyclerview when using databinding.
 * Created by David Kapas on 3/13/2019.
 */
class RecyclerViewAdapter : RecyclerView.Adapter<BindingViewHolder>() {

    var column: Int = 0
    protected var items = ArrayList<ListItemViewModel>()
        private set
    private var viewTypes: List<Int>? = null
    private var itemLayouts: List<Int>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val itemLayout = itemLayouts?.get(viewTypes?.indexOf(viewType) ?: viewType)
        val dataBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.getContext()), itemLayout!!, parent, false)
        return BindingViewHolder(dataBinding)
    }

    override fun getItemCount(): Int {
        var itemCount = items.size
        if (itemCount > column) {
            itemCount = getRoundDownItemCount(itemCount)
        }
        return itemCount
    }

    override fun onBindViewHolder(holder: BindingViewHolder?, position: Int) {
        holder?.binding?.setVariable(BR.viewModel, items[position]);
        holder?.binding?.executePendingBindings()
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getViewType()
    }

    private fun getRoundDownItemCount(itemCount: Int): Int {
        return itemCount - itemCount % column
    }

    fun setItemLayout(itemlayout: Int) {
        itemLayouts = Collections.singletonList(itemlayout);
    }

    fun setItems(newItems: List<ListItemViewModel>) {
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }
}