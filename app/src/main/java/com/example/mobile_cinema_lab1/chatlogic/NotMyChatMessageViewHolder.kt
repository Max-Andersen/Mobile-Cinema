package com.example.mobile_cinema_lab1.chatlogic

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_cinema_lab1.R
import com.example.mobile_cinema_lab1.additionalmodels.ChatUIModel

class NotMyChatMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val authorAvatar = itemView.findViewById<ImageView>(R.id.userAvatar)
    private val commentText = itemView.findViewById<TextView>(R.id.messageText)
    private val authorName = itemView.findViewById<TextView>(R.id.authorName)

    fun bind(data: ChatUIModel.NotMyMessageModel) {
        val context = itemView.context

        if (data.notMyMessage.showAvatar) {
            if (data.notMyMessage.authorAvatar != null) {
                Glide.with(context).load(data.notMyMessage.authorAvatar)
                    .into(authorAvatar)
            } else {
                Glide.with(context).clear(authorAvatar)
                authorAvatar.setImageResource(R.drawable.empty_profile_photo)
            }
            authorAvatar.visibility = View.VISIBLE
        } else {
            Glide.with(context).clear(authorAvatar)
            authorAvatar.visibility = View.INVISIBLE
        }

        commentText.text = data.notMyMessage.text
        authorName.text = String.format(
            context.getString(R.string.author_plus_time),
            data.notMyMessage.authorName,
            data.notMyMessage.creationDate.toString().subSequence(11, 16).toString()
        )
    }
}