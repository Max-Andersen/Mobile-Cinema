package com.example.mobile_cinema_lab1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_cinema_lab1.network.models.Movie

class CardStackAdapter(
    private var movies: List<Movie> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.card_stack_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        Glide.with(holder.image)
            .load(movie.poster)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.cardImage)
    }

}
