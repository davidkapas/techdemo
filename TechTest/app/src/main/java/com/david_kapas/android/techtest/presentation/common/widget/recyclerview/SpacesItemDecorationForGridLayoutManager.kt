package com.david_kapas.android.techtest.presentation.common.widget.recyclerview

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Space Item decorator class for GridLayoutManager.
 * Created by David Kapas on 3/13/2019.
 */
class SpacesItemDecorationForGridLayoutManager(val space: Int, val numColumns: Int) : RecyclerView.ItemDecoration() {

    var start: Int = 0;
    var end: Int = 0;

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = space
        outRect.top = 0
        start = 0
        end = 0
        if (numColumns == 2) {
            setOffsetForTwoColumn(view, parent)
        } else if (numColumns == 3) {
            setOffsetForThreeColumn(view, parent)
        }
        outRect.right = end
        outRect.left = start
    }

    private fun setOffsetForTwoColumn(view: View, parent: RecyclerView) {
        val position = parent.getChildLayoutPosition(view)
        val spanSizeLookup = (parent.layoutManager as GridLayoutManager).spanSizeLookup.getSpanIndex(position, 2)
        when (spanSizeLookup) {
            0 -> end = space / 2
            1 -> start = space / 2
        }
    }

    private fun setOffsetForThreeColumn(view: View, parent: RecyclerView) {
        val position = parent.getChildLayoutPosition(view)
        val spanSizeLookup = (parent.layoutManager as GridLayoutManager).spanSizeLookup.getSpanIndex(position, 3)
        when (spanSizeLookup) {
            0 -> end = space * 2 / 3
            1 -> {
                end = space / 3
                start = space / 3
            }
            2 -> start = space * 2 / 3
        }
    }
}