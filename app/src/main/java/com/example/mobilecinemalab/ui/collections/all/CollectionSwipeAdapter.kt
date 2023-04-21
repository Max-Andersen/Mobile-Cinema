package com.example.mobilecinemalab.ui.collections.all

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilecinemalab.R
import com.example.mobilecinemalab.ui.collections.CollectionUIModel
import com.example.mobilecinemalab.ui._custombehavior.ISwipeAction

class CollectionSwipeAdapter(private val collections: MutableList<CollectionUIModel>, private val swipeAction: ISwipeAction) :
    RecyclerView.Adapter<CollectionItemViewHolder>(), ISwipeAction {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CollectionItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val v = layoutInflater.inflate(R.layout.collection_item, parent, false)
        return CollectionItemViewHolder(v)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1 // 0 equals Favourite collection
    }

    override fun getItemCount(): Int = collections.size

    override fun onBindViewHolder(holder: CollectionItemViewHolder, position: Int) {
        holder.bind(collections[position])
    }

    override fun deleteElement(position: Int) {
        swipeAction.deleteElement(position)
        notifyDataSetChanged()
    }
}