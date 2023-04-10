package com.example.mobile_cinema_lab1.chatlogic

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_cinema_lab1.R
import com.example.mobile_cinema_lab1.additionalmodels.ChatUIModel

class MyChatMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val authorAvatar = itemView.findViewById<ImageView>(R.id.userAvatar)
    private val commentText = itemView.findViewById<TextView>(R.id.messageText)
    private val authorName = itemView.findViewById<TextView>(R.id.authorName)
    private val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)
    private val errorIcon = itemView.findViewById<ImageView>(R.id.error)

    fun bind(data: ChatUIModel.MyMessageModel) {
        val context = itemView.context
        
        progressBar.visibility = if (data.myMessage.isLoading) View.VISIBLE else View.INVISIBLE
        if (data.myMessage.fail) {
            errorIcon.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
        } else {
            errorIcon.visibility = View.INVISIBLE
        }

        if (data.myMessage.showAvatar) {
            if (data.myMessage.authorAvatar != null) {
                Glide.with(context).load(data.myMessage.authorAvatar)
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
        commentText.text = data.myMessage.text
        authorName.text =
            String.format(
                context.getString(R.string.author_plus_time),
                data.myMessage.authorName,
                data.myMessage.creationDate.toString().subSequence(11, 16).toString()
            )

    }
}