package com.example.mobilecinemalab.ui.chat.chatlogic

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.mobilecinemalab.R
import com.example.mobilecinemalab.ui.chat.additionalmodels.ChatUIModel
import com.example.mobilecinemalab.databinding.NotMyChatMessgeBinding

class NotMyChatMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) ,
    SpecificBackgroundItem {

    private val binding by viewBinding(NotMyChatMessgeBinding::bind)
    fun bind(data: ChatUIModel.NotMyMessageModel) {
        val context = itemView.context

        if (data.notMyMessage.showAvatar) {
            if (data.notMyMessage.authorAvatar != null) {
                Glide.with(context).load(data.notMyMessage.authorAvatar)
                    .into(binding.userAvatar)
            } else {
                Glide.with(context).clear(binding.userAvatar)
                binding.userAvatar.setImageResource(R.drawable.empty_profile_photo)
            }
            binding.userAvatar.visibility = View.VISIBLE
        } else {
            Glide.with(context).clear(binding.userAvatar)
            binding.userAvatar.visibility = View.INVISIBLE
        }

        binding.messageText.text = data.notMyMessage.text
        binding.authorName.text = String.format(
            context.getString(R.string.author_plus_time),
            data.notMyMessage.authorName,
            data.notMyMessage.creationDate.toString().subSequence(11, 16).toString()
        )
    }

    override fun getBackgroundView() = binding.constraintLayout
}