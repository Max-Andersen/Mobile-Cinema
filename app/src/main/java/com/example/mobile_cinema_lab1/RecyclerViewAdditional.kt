package com.example.mobile_cinema_lab1

import android.graphics.Canvas
import android.graphics.Rect
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_cinema_lab1.fragments.AllCollectionFragment
import com.example.mobile_cinema_lab1.fragments.SpecificCollectionFragment

class SimpleItemTouchHelperCollectionCallback (private val adapter: Any) :
    ItemTouchHelper.Callback() {

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val trashIcon = AppCompatResources.getDrawable(viewHolder.itemView.context, R.drawable.bin_icon)

        c.clipRect(0f, viewHolder.itemView.top.toFloat(), dX, viewHolder.itemView.bottom.toFloat())

        if (trashIcon != null) {
            trashIcon.bounds = Rect(
                30,
                viewHolder.itemView.top + 30,
                0 + trashIcon.intrinsicWidth,
                viewHolder.itemView.top + trashIcon.intrinsicHeight
                        - 50
            )
        }

        trashIcon?.draw(c)


        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = 0
        val swipeFlags = ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        when (adapter){
            is AllCollectionFragment.CollectionAdapter -> adapter.deleteCollection(viewHolder.absoluteAdapterPosition)
            is SpecificCollectionFragment.CollectionMovieAdapter -> adapter.deleteCollection(viewHolder.absoluteAdapterPosition)
        }

    }
}