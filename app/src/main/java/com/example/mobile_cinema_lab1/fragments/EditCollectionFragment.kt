package com.example.mobile_cinema_lab1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobile_cinema_lab1.MainActivity
import com.example.mobile_cinema_lab1.databinding.EditCollectionBinding

class EditCollectionFragment: Fragment() {
    private lateinit var binding: EditCollectionBinding

    private val args: EditCollectionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditCollectionBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).hideBottomNavigation()


        args.iconId?.let {
            // TODO load image into binding.selectedCollectionIcon
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.chooseCollectionIconButton.setOnClickListener {
            findNavController().navigate(EditCollectionFragmentDirections.actionEditCollectionFragmentToSelectCollectionIconFragment())
        }

        binding.enterCollectionName.setText(args.collectionName)

        return binding.root
    }
}