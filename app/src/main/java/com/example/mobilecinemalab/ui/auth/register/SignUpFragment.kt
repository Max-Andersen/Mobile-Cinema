package com.example.mobilecinemalab.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobilecinemalab.NavGraphXmlDirections
import com.example.mobilecinemalab.R
import com.example.mobilecinemalab.databinding.SignUpScreenBinding
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.domain.usecases.SharedPreferencesUseCase
import com.example.mobilecinemalab.forapplication.MainActivity
import com.example.mobilecinemalab.forapplication.errorhandling.ErrorDialogFragment

class SignUpFragment : Fragment() {
    private lateinit var binding: SignUpScreenBinding

    private val viewModel by lazy { ViewModelProvider(this)[SignUpViewModel::class.java] }

    private val sharedPreferencesUseCase =
        SharedPreferencesUseCase()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SignUpScreenBinding.inflate(inflater, container, false)

        binding.logUpButton.setOnClickListener {
            viewModel.register(
                binding.enterName.text.toString(),
                binding.enterSurname.text.toString(),
                binding.enterEmail.text.toString(),
                binding.enterPassword.text.toString(),
                binding.repeatPassword.text.toString()
            )
        }

        (requireActivity() as MainActivity).hideBottomNavigation()

        binding.toSignInButton.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveDataForValidation().observe(viewLifecycleOwner) {
            val dialogFragment = ErrorDialogFragment(it)
            dialogFragment.show(requireActivity().supportFragmentManager, "Problems")
        }

        viewModel.getLiveDataForRequest().observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Failure -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    val dialogFragment = ErrorDialogFragment(it.code)
                    dialogFragment.show(requireActivity().supportFragmentManager, "Problems")
                }
                is ApiResponse.Success -> {
                    sharedPreferencesUseCase.updateAccessToken(it.data.accessToken)
                    sharedPreferencesUseCase.updateRefreshToken(it.data.refreshToken)
                    viewModel.getUserInfo()
                }
                is ApiResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        viewModel.getLiveDataForUserInfo().observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Failure -> {
                    val errorDialog = ErrorDialogFragment(requireContext().getString(R.string.error_get_user_data))
                    errorDialog.show(requireActivity().supportFragmentManager, "Problems")
                }
                is ApiResponse.Success -> {
                    sharedPreferencesUseCase.updateUserId(it.data.userId)
                    sharedPreferencesUseCase.updateUserName(it.data.firstName + " " + it.data.lastName)
                    viewModel.createFavouriteCollection()
                }
                is ApiResponse.Loading -> {

                }
            }
        }

        viewModel.getLiveDataForFavouriteCollection().observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Failure -> {
                    val errorDialog = ErrorDialogFragment(requireContext().getString(R.string.error_create_favourite_collection))
                    errorDialog.show(requireActivity().supportFragmentManager, "Problems")
                }
                is ApiResponse.Success -> {
                    findNavController().navigate(NavGraphXmlDirections.actionGlobalMainFragment())
                }
                is ApiResponse.Loading -> {

                }
            }
        }
    }
}