package com.example.mobile_cinema_lab1.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.mobile_cinema_lab1.MainActivity
import com.example.mobile_cinema_lab1.NavGraphXmlDirections
import com.example.mobile_cinema_lab1.databinding.ProfileScreenBinding
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.Network
import com.example.mobile_cinema_lab1.viewmodels.ProfileViewModel

class ProfileFragment: Fragment() {
    private lateinit var binding: ProfileScreenBinding

    private val viewModel by lazy { ViewModelProvider(this)[ProfileViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileScreenBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).showBottomNavigation()

        viewModel.getUserInfo()

        viewModel.getLiveDataForUseInfo().observe(viewLifecycleOwner){
            when (it) {
                ApiResponse.Loading -> {

                }
                is ApiResponse.Failure -> {
                    Log.d("!", "Fail")
                }
                is ApiResponse.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.dataGroup.visibility = View.VISIBLE
                    binding.userName.text = it.data.firstName + " " + it.data.lastName
                    binding.userEmail.text = it.data.email

                }
            }
        }

        binding.exitButton.setOnClickListener {
            Network.clearUserData()
            findNavController().navigate(NavGraphXmlDirections.actionGlobalSignInFragment())
        }


        return binding.root
    }

}