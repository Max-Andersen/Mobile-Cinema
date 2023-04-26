package com.example.mobilecinemalab.ui.movie.images

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.mobilecinemalab.databinding.HorizontalImageItemForRecyclerviewBinding

class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding by viewBinding(HorizontalImageItemForRecyclerviewBinding::bind)

    fun bind(data: String) {
        Glide.with(itemView.context).load(data).into(binding.collectionImage)
    }
}