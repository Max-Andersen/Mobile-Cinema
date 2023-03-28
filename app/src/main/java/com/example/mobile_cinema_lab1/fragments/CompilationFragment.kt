package com.example.mobile_cinema_lab1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobile_cinema_lab1.databinding.CompilationScreenBinding

class CompilationFragment : Fragment() {
    private lateinit var binding: CompilationScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CompilationScreenBinding.inflate(inflater, container, false)
        return binding.root
    }
}