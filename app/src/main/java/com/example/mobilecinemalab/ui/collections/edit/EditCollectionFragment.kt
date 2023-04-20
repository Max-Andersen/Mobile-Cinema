package com.example.mobilecinemalab.ui.collections.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobilecinemalab.ui.collections.CollectionIconsEnum
import com.example.mobilecinemalab.forapplication.MainActivity
import com.example.mobilecinemalab.databinding.EditCollectionBinding
import com.example.mobilecinemalab.ui.collections.SelectCollectionIconFragment

class EditCollectionFragment: Fragment() {
    private lateinit var binding: EditCollectionBinding

    private val args: EditCollectionFragmentArgs by navArgs()

    private val viewModel by lazy { ViewModelProvider(this)[EditCollectionViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditCollectionBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).hideBottomNavigation()

        setFragmentResultListener(SelectCollectionIconFragment.ICON_RESULT_KEY) { _, bundle ->
            viewModel.selectedIcon = bundle.getString("icon")!!
            binding.selectedCollectionIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), CollectionIconsEnum.icons[viewModel.selectedIcon]!!))
        }


        args.iconId.let {
            binding.selectedCollectionIcon.setImageResource(CollectionIconsEnum.icons[it]!!)
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.chooseCollectionIconButton.setOnClickListener {
            findNavController().navigate(EditCollectionFragmentDirections.actionEditCollectionFragmentToSelectCollectionIconFragment())
        }

        binding.saveButton.setOnClickListener {
            viewModel.saveChanges(args.collectionShortModel.collectionId, binding.enterCollectionName.text.toString())
            findNavController().navigateUp()
        }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteCollection(args.collectionShortModel.collectionId)
            findNavController().navigate(EditCollectionFragmentDirections.actionEditCollectionFragmentToCollectionFragment())
        }

        binding.enterCollectionName.setText(args.collectionShortModel.collectionName)

        return binding.root
    }
}