package com.example.mobile_cinema_lab1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.mobile_cinema_lab1.MyApplication
import com.example.mobile_cinema_lab1.R
import com.example.mobile_cinema_lab1.additionalmodels.*
import com.example.mobile_cinema_lab1.databinding.ChatScreenBinding
import com.example.mobile_cinema_lab1.network.Network
import com.example.mobile_cinema_lab1.viewmodels.ChatViewModel
import kotlinx.datetime.LocalDateTime


class ChatFragment : Fragment() {

    private lateinit var binding: ChatScreenBinding

    private val viewModel by lazy { ViewModelProvider(this)[ChatViewModel::class.java] }

    private val messages = arrayListOf<ChatUIModel>()

    private lateinit var adapter: ChatAdapter

    private val args: ChatFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChatScreenBinding.inflate(inflater, container, false)

        binding.chatName.text = args.chatInfo.chatName

        adapter = ChatAdapter(messages)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)

        viewModel.getSocket(args.chatInfo.chatId)


        viewModel.getChatLiveData().observe(viewLifecycleOwner) {
            if (binding.progressBar.visibility == View.VISIBLE) binding.progressBar.visibility =
                View.INVISIBLE

            val uiModel: ChatUIModel

            if (messages.size != 0) {
                if (getCreationDateByMessageModel(messages.last()) != null) {
                    if (getCreationDateByMessageModel(messages.last())!!.toString()
                            .subSequence(0, 10)
                            .toString() < LocalDateTime.parse(it.creationDateTime).toString()
                            .subSequence(0, 10).toString()
                    ) {
                        val dateModel =
                            ChatUIModel.DaySeparationModel(DaySeparation(LocalDateTime.parse(it.creationDateTime)))
                        messages.add(dateModel)
                        adapter.notifyItemChanged(messages.size - 1)
                    }
                }

                val prevMessageUserId = getUserIdByMessageModel(messages.last())
                val curMessageUserId = it.authorId

                if (prevMessageUserId == curMessageUserId) {
                    if ((messages.last() as? ChatUIModel.MyMessageModel) != null) {
                        (messages.last() as ChatUIModel.MyMessageModel).myMessage.showAvatar =
                            false
                        adapter.notifyItemChanged(messages.size - 1)
                    }
                    if ((messages.last() as? ChatUIModel.NotMyMessageModel) != null) {
                        (messages.last() as ChatUIModel.NotMyMessageModel).notMyMessage.showAvatar =
                            false
                        adapter.notifyItemChanged(messages.size - 1)
                    }
                }
            }
            else{
                val dateModel =
                    ChatUIModel.DaySeparationModel(DaySeparation(LocalDateTime.parse(it.creationDateTime)))
                messages.add(dateModel)
                adapter.notifyItemChanged(messages.size - 1)
            }

            if (it.authorId == Network.getSharedPrefs(MyApplication.UserId)) {
                uiModel = ChatUIModel.MyMessageModel(
                    MyMessage(
                        creationDate = LocalDateTime.parse(it.creationDateTime),
                        authorId = it.authorId,
                        authorAvatar = it.authorAvatar,
                        authorName = it.authorName,
                        text = it.text,
                        isLoading = false
                    )
                )
            } else {
                uiModel = ChatUIModel.NotMyMessageModel(
                    NotMyMessage(
                        creationDate = LocalDateTime.parse(it.creationDateTime),
                        authorId = it.authorId,
                        authorAvatar = it.authorAvatar,
                        authorName = it.authorName,
                        text = it.text,
                    )
                )
            }

            messages.add(uiModel)
            adapter.notifyItemChanged(messages.size - 1)
            binding.recyclerView.scrollToPosition(messages.size - 1)
        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.closeSocket()
                    findNavController().navigateUp()

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


        binding.sendMessage.setOnClickListener {
            viewModel.sendMessage(binding.editTextMessage.text.toString())
        }

        binding.backButton.setOnClickListener {
            viewModel.closeSocket()
            findNavController().navigateUp()
        }

        return binding.root
    }

    private inner class ChatAdapter(private var arrayList: ArrayList<ChatUIModel>) :
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
                R.layout.my_chat_message -> this@ChatFragment.MyChatMessageViewHolder(v)
                R.layout.not_my_chat_messge -> this@ChatFragment.NotMyChatMessageViewHolder(v)
                else -> this@ChatFragment.DaySeparationViewHolder(v)
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = arrayList[position]

            if (position != 0) {
                val prevMessageUserId = getUserIdByMessageModel(arrayList[position])
                val curMessageUserId = getUserIdByMessageModel(arrayList[position - 1])

                val scale = resources.displayMetrics.density
                var sizeInDp: Int

                sizeInDp = if (prevMessageUserId == curMessageUserId) {
                    if (arrayList[position] as? ChatUIModel.MyMessageModel != null) {
                        holder.itemView.findViewById<ConstraintLayout>(R.id.constraintLayout).background =
                            AppCompatResources.getDrawable(
                                requireContext(),
                                R.drawable.message_item_my_double
                            )
                    }
                    if (arrayList[position] as? ChatUIModel.NotMyMessageModel != null) {
                        holder.itemView.findViewById<ConstraintLayout>(R.id.constraintLayout).background =
                            AppCompatResources.getDrawable(
                                requireContext(),
                                R.drawable.message_item_not_mine_double
                            )
                    }
                    4
                } else {
                    if (arrayList[position] as? ChatUIModel.MyMessageModel != null) {
                        holder.itemView.findViewById<ConstraintLayout>(R.id.constraintLayout).background =
                            AppCompatResources.getDrawable(
                                requireContext(),
                                R.drawable.message_item_my
                            )
                    }
                    if (arrayList[position] as? ChatUIModel.NotMyMessageModel != null) {
                        holder.itemView.findViewById<ConstraintLayout>(R.id.constraintLayout).background =
                            AppCompatResources.getDrawable(
                                requireContext(),
                                R.drawable.message_item_not_mine
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

    private inner class MyChatMessageViewHolder(view: View) : ViewHolder(view) {
        private lateinit var data: ChatUIModel.MyMessageModel
        private val authorAvatar = itemView.findViewById<ImageView>(R.id.userAvatar)
        private val commentText = itemView.findViewById<TextView>(R.id.messageText)
        private val authorName = itemView.findViewById<TextView>(R.id.authorName)
        private val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar) // TODO()

        fun bind(data: ChatUIModel.MyMessageModel) {
            this.data = data
            if (data.myMessage.showAvatar) {
                if (data.myMessage.authorAvatar != null) {
                    Glide.with(requireActivity()).load(data.myMessage.authorAvatar)
                        .into(authorAvatar)
                } else {
                    authorAvatar.setImageResource(R.drawable.empty_profile_photo)
                }
            }
            commentText.text = data.myMessage.text
            authorName.text =
                String.format(
                    getString(R.string.author_plus_time),
                    data.myMessage.authorName,
                    data.myMessage.creationDate.toString().subSequence(11, 16).toString()
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

            if (data.notMyMessage.showAvatar) {
                if (data.notMyMessage.authorAvatar != null) {
                    Glide.with(requireActivity()).load(data.notMyMessage.authorAvatar)
                        .into(authorAvatar)
                } else {
                    authorAvatar.setImageResource(R.drawable.empty_profile_photo)
                }
            }
            commentText.text = data.notMyMessage.text
            authorName.text = String.format(
                getString(R.string.author_plus_time),
                data.notMyMessage.authorName,
                data.notMyMessage.creationDate.toString().subSequence(11, 16).toString()
            )
        }
    }

    inner class DaySeparationViewHolder(view: View) : ViewHolder(view) {
        private lateinit var data: ChatUIModel.DaySeparationModel
        private val dateText = itemView.findViewById<TextView>(R.id.dateText)

        fun bind(data: ChatUIModel.DaySeparationModel) {
            this.data = data
            dateText.text = data.daySeparation.date.toString().subSequence(0, 10).toString()
        }
    }

}