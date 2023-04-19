package com.example.mobile_cinema_lab1.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobile_cinema_lab1.forapplication.MainActivity
import com.example.mobile_cinema_lab1.NavGraphXmlDirections
import com.example.mobile_cinema_lab1.databinding.SignUpScreenBinding
import com.example.mobile_cinema_lab1.forapplication.errorhandling.ErrorDialogFragment

class SignUpFragment : Fragment() {
    private lateinit var binding: SignUpScreenBinding

    private val viewModel by lazy { ViewModelProvider(this)[SignUpViewModel::class.java] }

    private val sharedPreferencesUseCase =
        com.example.mobile_cinema_lab1.domain.usecases.SharedPreferencesUseCase()

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
                is com.example.mobile_cinema_lab1.datasource.network.ApiResponse.Failure -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    val dialogFragment = ErrorDialogFragment(it.errorMessage)
                    dialogFragment.show(requireActivity().supportFragmentManager, "Problems")
                }
                is com.example.mobile_cinema_lab1.datasource.network.ApiResponse.Success -> {
                    sharedPreferencesUseCase.updateAccessToken(it.data.accessToken)
                    sharedPreferencesUseCase.updateRefreshToken(it.data.refreshToken)
                    viewModel.getUserInfo()
                }
                is com.example.mobile_cinema_lab1.datasource.network.ApiResponse.Loading -> {binding.progressBar.visibility = View.VISIBLE}
            }
        }

        viewModel.getLiveDataForUserInfo().observe(viewLifecycleOwner){
            when (it){
                is com.example.mobile_cinema_lab1.datasource.network.ApiResponse.Failure -> {
                    // TODO( Problems )
                }
                is com.example.mobile_cinema_lab1.datasource.network.ApiResponse.Success -> {
                    sharedPreferencesUseCase.updateUserId(it.data.userId)
                    sharedPreferencesUseCase.updateUserName(it.data.firstName + " " + it.data.lastName)
                    findNavController().navigate(NavGraphXmlDirections.actionGlobalMainFragment())
                }
                is com.example.mobile_cinema_lab1.datasource.network.ApiResponse.Loading -> {

                }
            }
        }
    }
}