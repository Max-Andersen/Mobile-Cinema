package com.example.mobile_cinema_lab1.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobile_cinema_lab1.MainActivity
import com.example.mobile_cinema_lab1.MyApplication
import com.example.mobile_cinema_lab1.NavGraphXmlDirections
import com.example.mobile_cinema_lab1.databinding.SignUpScreenBinding
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.Network
import com.example.mobile_cinema_lab1.viewmodels.SignUpViewModel

class SignUpFragment : Fragment() {
    private lateinit var binding: SignUpScreenBinding

    private val viewModel by lazy { ViewModelProvider(this)[SignUpViewModel::class.java] }

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
                    val dialogFragment = ErrorDialogFragment(it.errorMessage)
                    dialogFragment.show(requireActivity().supportFragmentManager, "Problems")
                }
                is ApiResponse.Success -> {
                    Network.updateSharedPrefs(MyApplication.AccessToken, it.data.accessToken)
                    Network.updateSharedPrefs(MyApplication.RefreshToken, it.data.refreshToken)
                    findNavController().navigate(NavGraphXmlDirections.actionGlobalMainFragment())
                }
                is ApiResponse.Loading -> Log.d("!", "Loading")
            }
        }
    }

}