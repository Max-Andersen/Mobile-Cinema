package com.example.mobilecinemalab.ui.collections.specific

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilecinemalab.R
import com.example.mobilecinemalab.databinding.SpecificCollectionScreenBinding
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.forapplication.MainActivity
import com.example.mobilecinemalab.forapplication.errorhandling.ErrorDialogFragment
import com.example.mobilecinemalab.ui._custombehavior.SimpleItemTouchHelperCollectionCallback
import com.example.mobilecinemalab.ui._custombehavior.ISwipeAction

class SpecificCollectionFragment : Fragment() {
    private lateinit var binding: SpecificCollectionScreenBinding

    private val viewModel by lazy { ViewModelProvider(this)[SpecificCollectionViewModel::class.java] }

    private val args: SpecificCollectionFragmentArgs by navArgs()

    private lateinit var adapter: CollectionMovieActionI


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SpecificCollectionScreenBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).hideBottomNavigation()

        viewModel.getItems(args.collectionShortModel.collectionId)

        binding.collectionName.text = args.collectionShortModel.collectionName


        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.editCollection.setOnClickListener {
            if (args.collectionShortModel.collectionName != "Избранное"){
                findNavController().navigate(
                    SpecificCollectionFragmentDirections.actionSpecificCollectionFragmentToEditCollectionFragment(
                        args.iconId,
                        args.collectionShortModel
                    )
                )
            } else{
                Toast.makeText(requireContext(), "You cannot edit favourite collection!", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.getCollectionItemsLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    adapter.notifyDataSetChanged()
                }
                is ApiResponse.Failure -> {
                    val errorDialog = ErrorDialogFragment(requireContext().getString(R.string.error_get_collection_movies))
                    errorDialog.show(requireActivity().supportFragmentManager, "Problems")
                }
                else -> {}
            }
        }

        adapter = CollectionMovieActionI(viewModel.movies, object : ISwipeAction {
            override fun deleteElement(position: Int) {
                viewModel.deleteMovieFromCollection(position)
            }
        })

        binding.collectionItemsRecyclerView.adapter = adapter
        binding.collectionItemsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        val callback = SimpleItemTouchHelperCollectionCallback(adapter)
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(binding.collectionItemsRecyclerView)

        return binding.root
    }





}