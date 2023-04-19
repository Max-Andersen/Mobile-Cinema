package com.example.mobile_cinema_lab1.ui.collections.specific

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_cinema_lab1.R
import com.example.mobile_cinema_lab1.datasource.network.models.Movie
import com.example.mobile_cinema_lab1.ui.collections.ISwipeAction

class CollectionMovieActionI(
    private var movies: ArrayList<Movie>,
    private var swipeAction: ISwipeAction
) :
    RecyclerView.Adapter<CollectionMovieItemViewHolder>(), ISwipeAction {
    override fun getItemCount(): Int = movies.size

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CollectionMovieItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val v = layoutInflater.inflate(R.layout.collection_movie_item, parent, false)
        return CollectionMovieItemViewHolder(v)
    }

    override fun onBindViewHolder(holder: CollectionMovieItemViewHolder, position: Int) {
        val item = movies[position]
        holder.bind(item)
    }

    override fun deleteElement(position: Int) {
        swipeAction.deleteElement(position)
        notifyDataSetChanged()
    }
}