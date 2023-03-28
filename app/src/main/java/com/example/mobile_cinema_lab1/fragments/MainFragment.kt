package com.example.mobile_cinema_lab1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_cinema_lab1.databinding.MainFrameBinding
import com.example.mobile_cinema_lab1.viewmodels.MainFragmentViewModel

class MainFragment: Fragment() {
    private lateinit var binding: MainFrameBinding

    private val viewModel by lazy { ViewModelProvider(this)[MainFragmentViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFrameBinding.inflate(inflater, container, false)
        return binding.root
    }
}