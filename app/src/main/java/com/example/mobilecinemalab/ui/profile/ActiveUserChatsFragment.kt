package com.example.mobilecinemalab.ui.profile

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mobilecinemalab.forapplication.MainActivity
import com.example.mobilecinemalab.R
import com.example.mobilecinemalab.databinding.ActiveUserChatsScreenBinding
import com.example.mobilecinemalab.databinding.ChatItemBinding
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.ChatWithLastMessage
import com.example.mobilecinemalab.navigationmodels.Chat


class ActiveUserChatsFragment : Fragment() {

    private lateinit var binding: ActiveUserChatsScreenBinding

    private lateinit var adapter: ChatAdapter

    private val viewModel by lazy { ViewModelProvider(this)[ActiveUserChatsViewModel::class.java] }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActiveUserChatsScreenBinding.inflate(inflater, container, false)

        viewModel.getChats()

        adapter = ChatAdapter(viewModel.chatList)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = adapter


        viewModel.getChatsLiveData().observe(viewLifecycleOwner) {
            if (it is ApiResponse.Success || it is ApiResponse.Failure) {
                binding.progressBar.visibility = View.INVISIBLE
            }
            if (it is ApiResponse.Success) {
                adapter.notifyDataSetChanged()
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }


        (requireActivity() as MainActivity).hideBottomNavigation()

        return binding.root
    }

    private inner class ChatHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private lateinit var chatInfo: Chat
        private val binding by viewBinding(ChatItemBinding::bind)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(data: ChatWithLastMessage) {
            chatInfo = Chat(chatId = data.chatId, chatName = data.chatName)

            val words = chatInfo.chatName.split("\\s+".toRegex())

            if (words.size >= 2) {
                binding.chatNameShort.text = String.format(
                    itemView.context.getString(R.string.chat_short_name),
                    words[0][0],
                    words[1][0].uppercase()
                )
            } else {
                binding.chatNameShort.text = String.format(
                    itemView.context.getString(R.string.chat_short_name),
                    words[0][0],
                    words[0][1].uppercase()
                )
            }


            binding.chatName.text = data.chatName



            val word: Spannable = SpannableString(data.lastMessage.authorName + ": ")

            word.setSpan(
                ForegroundColorSpan(itemView.context.getColor(R.color.another_grey)),
                0,
                word.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            val wordTwo: Spannable = SpannableString(data.lastMessage.text)

            wordTwo.setSpan(
                ForegroundColorSpan(Color.WHITE),
                0,
                wordTwo.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            binding.lastMessage.text = TextUtils.concat(word, wordTwo)


            //binding.lastMessage.text = data.lastMessage.authorName + " " + data.lastMessage.text
        }

        override fun onClick(p0: View?) {
            findNavController().navigate(
                ActiveUserChatsFragmentDirections.actionActiveUserChatsFragmentToChatFragment(
                    chatInfo
                )
            )
        }


    }

    private inner class ChatAdapter(var chats: MutableList<ChatWithLastMessage>) :
        RecyclerView.Adapter<ChatHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
            val view =
                layoutInflater.inflate(
                    R.layout.chat_item,
                    parent,
                    false
                )
            return ChatHolder(view)
        }

        override fun getItemCount(): Int {
            return chats.size
        }

        override fun onBindViewHolder(holder: ChatHolder, position: Int) {
            val chat = chats[position]
            holder.bind(chat)
        }

    }
}



