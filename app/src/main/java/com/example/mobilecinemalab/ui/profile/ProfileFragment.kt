package com.example.mobilecinemalab.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mobilecinemalab.NavGraphXmlDirections
import com.example.mobilecinemalab.R
import com.example.mobilecinemalab.databinding.ProfileScreenBinding
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.domain.usecases.SharedPreferencesUseCase
import com.example.mobilecinemalab.forapplication.MainActivity
import com.example.mobilecinemalab.forapplication.errorhandling.ErrorDialogFragment
import com.example.mobilecinemalab.ui.profile.photo.ProfileAvatarChoiceDialog

class ProfileFragment : Fragment(), ProfileAvatarChoiceDialog.IReloadListener {
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
                    errorDialog.show(requireActivity().supportFragmentManager, getString(R.string.problems))
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

        binding.edit.setOnClickListener {
            val dialog = ProfileAvatarChoiceDialog()
            val bundle = Bundle()
            bundle.putString(
                "avatar",
                viewModel.avatar
            )
            dialog.arguments = bundle
            dialog.show(childFragmentManager, "profile_avatar_choice")
        }

        return binding.root
    }

    override fun reload() {
        viewModel.getUserInfo()
    }

}