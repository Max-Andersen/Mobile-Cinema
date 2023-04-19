package com.example.mobile_cinema_lab1.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mobile_cinema_lab1.forapplication.MainActivity
import com.example.mobile_cinema_lab1.NavGraphXmlDirections
import com.example.mobile_cinema_lab1.R
import com.example.mobile_cinema_lab1.databinding.ProfileScreenBinding
import com.example.mobile_cinema_lab1.datasource.network.ApiResponse
import com.example.mobile_cinema_lab1.domain.usecases.SharedPreferencesUseCase
import com.example.mobile_cinema_lab1.forapplication.errorhandling.ErrorDialogFragment

class ProfileFragment : Fragment() {
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

        viewModel.getLiveDataForUseInfo().observe(viewLifecycleOwner) {
            when (it) {
                ApiResponse.Loading -> {

                }
                is ApiResponse.Failure -> {
                    val errorDialog = ErrorDialogFragment(requireContext().getString(R.string.error_get_user_data))
                    errorDialog.show(requireActivity().supportFragmentManager, "Problems")
                }
                is ApiResponse.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.dataGroup.visibility = View.VISIBLE
                    binding.userName.text = String.format(
                        getString(R.string.first_and_last_name),
                        it.data.firstName,
                        it.data.lastName
                    )
                    binding.userEmail.text = it.data.email

                    it.data.avatar?.let { photo ->
                        Glide.with(requireContext()).load(photo).into(binding.userImageView)
                    }
                }
            }
        }

        binding.exitButton.setOnClickListener {
            SharedPreferencesUseCase()
                .clearUserData()
            findNavController().navigate(NavGraphXmlDirections.actionGlobalSignInFragment())
        }

        binding.discussionsLayer.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToActiveUserChatsFragment())
        }

        return binding.root
    }

}