package com.example.mobile_cinema_lab1.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobile_cinema_lab1.forapplication.MainActivity
import com.example.mobile_cinema_lab1.NavGraphXmlDirections
import com.example.mobile_cinema_lab1.databinding.SignInScreenBinding
import com.example.mobile_cinema_lab1.forapplication.errorhandling.ErrorDialogFragment
import com.example.mobile_cinema_lab1.datasource.network.ApiResponse.*

class SignInFragment : Fragment() {
    private lateinit var binding: SignInScreenBinding

    private val viewModel by lazy { ViewModelProvider(this)[SignInViewModel::class.java] }

    private val sharedPreferencesUseCase =
        com.example.mobile_cinema_lab1.domain.usecases.SharedPreferencesUseCase()

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
        (requireActivity() as MainActivity).hideBottomNavigation()
        binding.toSignUpButton.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveDataForValidation().observe(viewLifecycleOwner) {
            val dialogFragment = ErrorDialogFragment(it)
            dialogFragment.show(requireActivity().supportFragmentManager, "Problems")
        }

        viewModel.getLiveDataForRequest().observe(viewLifecycleOwner){
            when (it){
                is Failure -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    val dialogFragment = ErrorDialogFragment(it.errorMessage)
                    dialogFragment.show(requireActivity().supportFragmentManager, "Problems")
                }
                is Success -> {
                    sharedPreferencesUseCase.updateAccessToken(it.data.accessToken)
                    sharedPreferencesUseCase.updateRefreshToken(it.data.refreshToken)

                    viewModel.getUserInfo()
                }
                is Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        viewModel.getLiveDataForUserInfo().observe(viewLifecycleOwner){
            when (it){
                is Failure -> {
                    // TODO( Problems )
                }
                is Success -> {
                    sharedPreferencesUseCase.updateUserId(it.data.userId)
                    sharedPreferencesUseCase.updateUserName(it.data.firstName + " " + it.data.lastName)
                    findNavController().navigate(NavGraphXmlDirections.actionGlobalMainFragment())
                }
                is Loading -> {

                }
            }
        }
    }
}