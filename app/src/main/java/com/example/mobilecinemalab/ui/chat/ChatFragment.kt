package com.example.mobilecinemalab.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.mobilecinemalab.ui.chat.additionalmodels.ChatUIModel
import com.example.mobilecinemalab.ui.chat.chatlogic.ChatAdapter
import com.example.mobilecinemalab.databinding.ChatScreenBinding
import com.example.mobilecinemalab.forapplication.errorhandling.ErrorDialogFragment


class ChatFragment : Fragment() {

    private lateinit var binding: ChatScreenBinding

    private val viewModel by lazy { ViewModelProvider(this)[ChatViewModel::class.java] }

    private lateinit var adapter: ChatAdapter

    private val args: ChatFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChatScreenBinding.inflate(inflater, container, false)

        binding.chatName.text = args.chatInfo.chatName

        adapter = ChatAdapter(viewModel.messages)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false).apply { stackFromEnd = true }

        viewModel.getSocket(args.chatInfo.chatId)

        viewModel.getUpdateStateRecyclerViewLiveData().observe(viewLifecycleOwner) {
            if (binding.progressBar.visibility == View.VISIBLE) binding.progressBar.visibility =
                View.INVISIBLE
            adapter.notifyItemChanged(it)
            binding.recyclerView.scrollToPosition( adapter.itemCount - 1 )
        }

        viewModel.getValidationLiveData().observe(viewLifecycleOwner) {
            val dialogFragment = ErrorDialogFragment(it)
            dialogFragment.show(requireActivity().supportFragmentManager, "Problems")
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
            binding.editTextMessage.text.clear()
            binding.recyclerView.scrollToPosition( adapter.itemCount - 1 )
        }

        binding.backButton.setOnClickListener {
            callback.handleOnBackPressed()
        }

        return binding.root
    }

    private fun printMessagesData(messages: java.util.ArrayList<ChatUIModel>) {
        var i = 0
        messages.forEach {
            if (it as? ChatUIModel.MyMessageModel != null) {
                Log.d("check", "$i   ${it.myMessage.text}")
                Log.d("check", "$i   ${it.myMessage.showAvatar}")
                Log.d("check", "$i   ${it.myMessage.authorAvatar}")
            }

            if (it as? ChatUIModel.NotMyMessageModel != null) {
                Log.d("check", "$i   ${it.notMyMessage.text}")
                Log.d("check", "$i   ${it.notMyMessage.showAvatar}")
                Log.d("check", "$i   ${it.notMyMessage.authorAvatar}")
            }
            Log.d("check", "")
            i += 1
        }
    }
}