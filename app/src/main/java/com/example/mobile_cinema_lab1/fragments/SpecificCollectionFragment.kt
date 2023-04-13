package com.example.mobile_cinema_lab1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mobile_cinema_lab1.databinding.SpecificCollectionScreenBinding

class SpecificCollectionFragment: Fragment() {
    private lateinit var binding: SpecificCollectionScreenBinding

    private val args: SpecificCollectionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SpecificCollectionScreenBinding.inflate(inflater, container, false)

        binding.collectionName.text = args.collectionShortModel.collectionName

        return binding.root
    }


}