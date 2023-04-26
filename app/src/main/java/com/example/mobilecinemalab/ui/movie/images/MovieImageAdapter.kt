package com.example.mobilecinemalab.ui.movie.images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilecinemalab.R
import com.example.mobilecinemalab.navigationmodels.Movie

class MovieImageAdapter(var movie: Movie) :
    RecyclerView.Adapter<ImageHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view =
            layoutInflater.inflate(
                R.layout.horizontal_image_item_for_recyclerview,
                parent,
                false
            )
        return ImageHolder(view)
    }

    override fun getItemCount(): Int {
        return movie.imageUrls.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val movie = movie.imageUrls[position]
        holder.bind(movie)
    }


}