package com.example.mobile_cinema_lab1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobile_cinema_lab1.databinding.EditCollectionBinding

class EditCollectionFragment: Fragment() {
    private lateinit var binding: EditCollectionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditCollectionBinding.inflate(inflater, container, false)
        return binding.root
    }
}