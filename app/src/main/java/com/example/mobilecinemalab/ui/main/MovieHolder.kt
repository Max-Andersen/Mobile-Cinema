package com.example.mobilecinemalab.ui.main

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.mobilecinemalab.databinding.VerticalImageItemForRecyclerviewBinding
import com.example.mobilecinemalab.datasource.network.models.Movie
import com.example.mobilecinemalab.navigationmodels.getNavigationModel

class MovieHolder(view: View) : RecyclerView.ViewHolder(view),
    View.OnClickListener {
    private lateinit var data: Movie

    private val binding by viewBinding(VerticalImageItemForRecyclerviewBinding::bind)

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(data: Movie, image: String) {
        this.data = data
        Glide.with(itemView.context).load(image).into(binding.collectionImage)
    }

    override fun onClick(p0: View?) {
        itemView.findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToMovieFragment(
                data.getNavigationModel()
            )
        )
    }
}