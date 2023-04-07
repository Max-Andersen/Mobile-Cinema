package com.example.mobile_cinema_lab1.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.mobile_cinema_lab1.R
import com.example.mobile_cinema_lab1.additionalmodels.ChatUIModel
import com.example.mobile_cinema_lab1.databinding.ChatScreenBinding
import com.example.mobile_cinema_lab1.viewmodels.ChatViewModel
import kotlinx.datetime.LocalDateTime

class ChatFragment : Fragment() {

    private lateinit var binding: ChatScreenBinding

    private val viewModel by lazy { ViewModelProvider(this)[ChatViewModel::class.java] }

    private val args: ChatFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChatScreenBinding.inflate(inflater, container, false)

        viewModel.getSocket(args.chatId)

        viewModel.getChatLiveData().observe(viewLifecycleOwner){
            //Log.d("!", LocalDateTime.parse(it.creationDateTime).toString() )
            Log.d("!", it.text)
        }

        return binding.root
    }


    class ChatAdapter(private var arrayList: ArrayList<ChatUIModel>) :
        RecyclerView.Adapter<ViewHolder>() {

        override fun getItemCount(): Int = arrayList.size

        override fun getItemViewType(position: Int) = when (arrayList[position]) {
            is ChatUIModel.MyMessageModel -> R.layout.my_chat_message
            is ChatUIModel.NotMyMessageModel -> R.layout.not_my_chat_messge
            is ChatUIModel.DaySeparationModel -> R.layout.day_separation
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val v = layoutInflater.inflate(viewType, parent, false)
            return when (viewType) {
                R.layout.my_chat_message -> ChatFragment().MyChatMessageViewHolder(v)
                R.layout.not_my_chat_messge -> ChatFragment().NotMyChatMessageViewHolder(v)
                else -> ChatFragment().DaySeparationViewHolder(v)
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = arrayList[position]
            when (holder) {
                is MyChatMessageViewHolder -> holder.bind(item as ChatUIModel.MyMessageModel)
                is NotMyChatMessageViewHolder -> holder.bind(item as ChatUIModel.NotMyMessageModel)
                is DaySeparationViewHolder -> holder.bind(item as ChatUIModel.DaySeparationModel)
            }
        }
    }

    inner class MyChatMessageViewHolder(view: View) : ViewHolder(view) {
        private lateinit var data: ChatUIModel.MyMessageModel
        private val authorAvatar = itemView.findViewById<ImageView>(R.id.userAvatar)
        private val commentText = itemView.findViewById<TextView>(R.id.messageText)
        private val authorName = itemView.findViewById<TextView>(R.id.authorName)
        private val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar) // TODO()

        fun bind(data: ChatUIModel.MyMessageModel) {
            this.data = data
            Glide.with(requireActivity()).load(data.myMessage.authorAvatar).into(authorAvatar)
            commentText.text = data.myMessage.text
            authorName.text = String.format(
                getString(R.string.author_plus_date),
                data.myMessage.authorName,
                data.myMessage.creationDate
            )
        }
    }

    inner class NotMyChatMessageViewHolder(view: View) : ViewHolder(view) {
        private lateinit var data: ChatUIModel.NotMyMessageModel
        private val authorAvatar = itemView.findViewById<ImageView>(R.id.userAvatar)
        private val commentText = itemView.findViewById<TextView>(R.id.messageText)
        private val authorName = itemView.findViewById<TextView>(R.id.authorName)

        fun bind(data: ChatUIModel.NotMyMessageModel) {
            this.data = data
            Glide.with(requireActivity()).load(data.notMyMessage.authorAvatar).into(authorAvatar)
            commentText.text = data.notMyMessage.text
            authorName.text = String.format(
                getString(R.string.author_plus_date),
                data.notMyMessage.authorName,
                data.notMyMessage.creationDate
            )
        }
    }

    inner class DaySeparationViewHolder(view: View) : ViewHolder(view) {
        private lateinit var data: ChatUIModel.DaySeparationModel
        private val dateText = itemView.findViewById<TextView>(R.id.dateText)

        fun bind(data: ChatUIModel.DaySeparationModel) {
            this.data = data
            dateText.text = data.daySeparation.date.toString()
        }
    }

}