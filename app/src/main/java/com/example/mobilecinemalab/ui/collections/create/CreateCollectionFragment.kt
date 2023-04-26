package com.example.mobilecinemalab.ui.collections.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobilecinemalab.R
import com.example.mobilecinemalab.ui.collections.CollectionIconsEnum
import com.example.mobilecinemalab.databinding.CreateCollectionBinding
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.ui.collections.SelectCollectionIconFragment

class CreateCollectionFragment : Fragment() {
    private lateinit var binding: CreateCollectionBinding

    private val viewModel by lazy { ViewModelProvider(this)[CreateCollectionViewModel::class.java] }

    private val icons = CollectionIconsEnum.icons

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreateCollectionBinding.inflate(inflater, container, false)

        setFragmentResultListener(SelectCollectionIconFragment.ICON_RESULT_KEY) { _, bundle ->
            viewModel.selectedIcon = bundle.getString(getString(R.string.icon))!!
            binding.selectedCollectionIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), icons[viewModel.selectedIcon]!!))
        }

        binding.selectedCollectionIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), icons[viewModel.selectedIcon]!!))

        binding.chooseCollectionIconButton.setOnClickListener {
            findNavController().navigate(CreateCollectionFragmentDirections.actionCreateCollectionFragmentToSelectCollectionIconFragment())
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.saveButton.setOnClickListener {
            viewModel.createCollection(binding.enterCollectionName.text.toString())
        }

        viewModel.getCreateCollectionLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ApiResponse.Failure -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_LONG).show()
                }
                is ApiResponse.Success -> {
                    findNavController().navigateUp()
                }
            }
        }

        return binding.root
    }
}