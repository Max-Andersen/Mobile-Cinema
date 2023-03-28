package com.example.mobile_cinema_lab1.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_cinema_lab1.databinding.SignInScreenBinding
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.viewmodels.SignInViewModel

class SignInFragment : Fragment() {
    private lateinit var binding: SignInScreenBinding

    private val viewModel by lazy { ViewModelProvider(this)[SignInViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SignInScreenBinding.inflate(inflater, container, false)
        binding.signInButton.setOnClickListener {
            viewModel.login(
                binding.enterEmail.text.toString(),
                binding.enterPassword.text.toString()
            )
        }
        binding.toSignUpButton.setOnClickListener {
            //TODO(Routing to sign up screen)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveDataForValidation().observe(viewLifecycleOwner) {
            //TODO(Dialog with error answer from viewModel)
            viewModel.getLiveDataForValidation().value = ""
        }

        viewModel.getLiveDataForRequest().observe(viewLifecycleOwner){
            when (it){
                is ApiResponse.Failure -> Log.d("!", it.errorMessage)
                is ApiResponse.Success -> Log.d("!", it.data.accessToken)
                is ApiResponse.Loading -> Log.d("!", "Loading")
            }
        }
    }
}