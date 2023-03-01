package com.example.tasks.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeHelper(private val adapter: OnSwipeAdapter) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
    ): Int {
        val flags = ItemTouchHelper.RIGHT
        return makeMovementFlags(0, flags)
    }

    override fun getMoveThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.3f
    }

    override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
        adapter.onItemSwipe(vh.absoluteAdapterPosition)
    }

    override fun onMove(
        rv: RecyclerView,
        vh: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ) = false
}