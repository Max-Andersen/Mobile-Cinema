package com.example.mobile_cinema_lab1.ui.chat.chatlogic

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.mobile_cinema_lab1.R
import com.example.mobile_cinema_lab1.ui.chat.additionalmodels.ChatUIModel
import com.example.mobile_cinema_lab1.databinding.MyChatMessageBinding

class MyChatMessageViewHolder(view: View) : RecyclerView.ViewHolder(view), SpecificBackgroundItem {

    private val binding by viewBinding(MyChatMessageBinding::bind)

    fun bind(data: ChatUIModel.MyMessageModel) {
        val context = itemView.context

        binding.progressBar.visibility = if (data.myMessage.isLoading) View.VISIBLE else View.INVISIBLE
        if (data.myMessage.fail) {
            binding.error.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        } else {
            binding.error.visibility = View.INVISIBLE
        }

        if (data.myMessage.showAvatar) {
            if (data.myMessage.authorAvatar != null) {
                Glide.with(context).load(data.myMessage.authorAvatar)
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
        binding.messageText.text = data.myMessage.text
        binding.authorName.text =
            String.format(
                context.getString(R.string.author_plus_time),
                data.myMessage.authorName,
                data.myMessage.creationDate.toString().subSequence(11, 16).toString()
            )

    }

    override fun getBackgroundView() = binding.constraintLayout
}