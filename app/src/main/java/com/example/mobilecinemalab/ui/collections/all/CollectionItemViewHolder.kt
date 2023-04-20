package com.example.mobilecinemalab.ui.collections.all

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mobilecinemalab.databinding.CollectionItemBinding
import com.example.mobilecinemalab.ui.collections.CollectionIconsEnum
import com.example.mobilecinemalab.ui.collections.CollectionUIModel
import com.example.mobilecinemalab.ui.collections.getNavigationModel

class CollectionItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
    private val binding by viewBinding(CollectionItemBinding::bind)
    private lateinit var data: CollectionUIModel

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(data: CollectionUIModel) {
        this.data = data
        binding.collectionName.text = data.name
        if (this.data.iconId.isBlank()) this.data.iconId = "1"

        binding.collectionImage.setImageResource(CollectionIconsEnum.icons[this.data.iconId]!!)
    }

    override fun onClick(p0: View?) {
        itemView.findNavController().navigate(
            AllCollectionFragmentDirections.actionCollectionFragmentToSpecificCollectionFragment(
                data.getNavigationModel(),
                data.iconId
            )
        )
    }
}