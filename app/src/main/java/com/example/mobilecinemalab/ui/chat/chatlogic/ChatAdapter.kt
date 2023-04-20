package com.example.mobilecinemalab.ui.chat.chatlogic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilecinemalab.R
import com.example.mobilecinemalab.ui.chat.additionalmodels.ChatUIModel
import com.example.mobilecinemalab.ui.chat.additionalmodels.getUserIdByMessageModel

class ChatAdapter(private var arrayList: ArrayList<ChatUIModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int = arrayList.size

    override fun getItemViewType(position: Int) = when (arrayList[position]) {
        is ChatUIModel.MyMessageModel -> R.layout.my_chat_message
        is ChatUIModel.NotMyMessageModel -> R.layout.not_my_chat_messge
        is ChatUIModel.DaySeparationModel -> R.layout.day_separation
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val v = layoutInflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.my_chat_message -> MyChatMessageViewHolder(v)
            R.layout.not_my_chat_messge -> NotMyChatMessageViewHolder(v)
            else -> DaySeparationViewHolder(v)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = arrayList[position]

        if (position > 1) { // first item
            val prevMessageUserId = getUserIdByMessageModel(arrayList[position])
            val curMessageUserId = getUserIdByMessageModel(arrayList[position - 1])

            val scale = holder.itemView.context.resources.displayMetrics.density
            var sizeInDp: Int

            sizeInDp = if (prevMessageUserId == curMessageUserId) {
                val drawableId = when (arrayList[position]) {
                    is ChatUIModel.MyMessageModel -> R.drawable.message_item_my_double
                    is ChatUIModel.NotMyMessageModel -> R.drawable.message_item_not_mine_double
                    else -> null
                }

                drawableId?.let {
                    (holder as SpecificBackgroundItem).getBackgroundView().background =
                        AppCompatResources.getDrawable(
                            holder.itemView.context,
                            it
                        )
                }

                4
            } else {
                val drawableId = when (arrayList[position]) {
                    is ChatUIModel.MyMessageModel -> R.drawable.message_item_my
                    is ChatUIModel.NotMyMessageModel -> R.drawable.message_item_not_mine
                    else -> null
                }

                drawableId?.let {
                    (holder as SpecificBackgroundItem).getBackgroundView().background =
                        AppCompatResources.getDrawable(
                            holder.itemView.context,
                            it
                        )
                }

                16
            }

            if (prevMessageUserId == null || curMessageUserId == null) {   // Prev or cur message - time separation
                sizeInDp = 24
            }

            val dpAsPixels = (sizeInDp * scale + 0.5f)

            holder.itemView.setPadding(0, dpAsPixels.toInt(), 0, 0)

        }

        when (holder) {
            is MyChatMessageViewHolder -> holder.bind(item as ChatUIModel.MyMessageModel)
            is NotMyChatMessageViewHolder -> holder.bind(item as ChatUIModel.NotMyMessageModel)
            is DaySeparationViewHolder -> holder.bind(item as ChatUIModel.DaySeparationModel)
        }
    }
}


interface SpecificBackgroundItem {
    fun getBackgroundView(): ConstraintLayout
}