package com.example.mobile_cinema_lab1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobile_cinema_lab1.MainActivity
import com.example.mobile_cinema_lab1.databinding.AllCollectionIconsBinding

class SelectCollectionIconFragment: Fragment() {
    private lateinit var binding: AllCollectionIconsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AllCollectionIconsBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).hideBottomNavigation()

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

}