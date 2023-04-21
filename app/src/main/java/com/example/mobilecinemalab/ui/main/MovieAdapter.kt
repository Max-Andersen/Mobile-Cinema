package com.example.mobilecinemalab.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilecinemalab.R
import com.example.mobilecinemalab.datasource.network.models.Movie

class MovieAdapter(var movies: List<Movie>, private val type: String) :
    RecyclerView.Adapter<MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view =
            layoutInflater.inflate(viewType, parent, false)
        return MovieHolder(view)
    }

    override fun getItemViewType(position: Int) = when(type){
        "vertical" -> R.layout.vertical_image_item_for_recyclerview
        else -> {R.layout.horizontal_image_item_for_recyclerview}
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = movies[position]
        when (getItemViewType(position)){
            R.layout.vertical_image_item_for_recyclerview -> holder.bind(movie, movie.poster)
            R.layout.horizontal_image_item_for_recyclerview -> holder.bind(movie, movie.imageUrls.first())
        }
    }

}