package com.example.mobilecinemalab.ui.chat.chatlogic

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mobilecinemalab.ui.chat.additionalmodels.ChatUIModel
import com.example.mobilecinemalab.databinding.DaySeparationBinding

class DaySeparationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding by viewBinding(DaySeparationBinding::bind)

    fun bind(data: ChatUIModel.DaySeparationModel) {
        binding.dateText.text = data.daySeparation.date.toString().subSequence(0, 10).toString()
    }
}