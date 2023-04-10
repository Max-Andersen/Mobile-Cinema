package com.example.mobile_cinema_lab1.chatlogic

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_cinema_lab1.R
import com.example.mobile_cinema_lab1.additionalmodels.ChatUIModel

class DaySeparationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val dateText = itemView.findViewById<TextView>(R.id.dateText)

    fun bind(data: ChatUIModel.DaySeparationModel) {
        dateText.text = data.daySeparation.date.toString().subSequence(0, 10).toString()
    }
}