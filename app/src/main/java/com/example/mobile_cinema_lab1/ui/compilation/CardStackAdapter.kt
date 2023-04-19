package com.example.mobile_cinema_lab1.ui.compilation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.mobile_cinema_lab1.R
import com.example.mobile_cinema_lab1.databinding.CardStackItemBinding
import com.example.mobile_cinema_lab1.datasource.network.models.Movie

class CardStackAdapter(
    private var movies: List<Movie> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.card_stack_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        Glide.with(holder.getImageView())
            .load(movie.poster)
            .into(holder.getImageView())
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding by viewBinding(CardStackItemBinding::bind)
        fun getImageView() = binding.cardImage
    }

}
