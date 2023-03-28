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
import com.example.mobile_cinema_lab1.databinding.SignInScreenBinding
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.Network
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