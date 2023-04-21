package com.example.mobilecinemalab.ui.collections.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilecinemalab.*
import com.example.mobilecinemalab.databinding.AllCollectionScreenBinding
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.forapplication.errorhandling.ErrorDialogFragment
import com.example.mobilecinemalab.forapplication.MainActivity
import com.example.mobilecinemalab.ui._custombehavior.ISwipeAction
import com.example.mobilecinemalab.ui._custombehavior.SimpleItemTouchHelperCollectionCallback

class AllCollectionFragment : Fragment() {
    private lateinit var binding: AllCollectionScreenBinding

    private val viewModel by lazy { ViewModelProvider(this)[AllCollectionsViewModel::class.java] }

    private lateinit var adapter: CollectionSwipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AllCollectionScreenBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).showBottomNavigation()

        adapter = CollectionSwipeAdapter(viewModel.userCollections, object : ISwipeAction {
            override fun deleteElement(position: Int) {
                viewModel.deleteCollection(position)
            }
        })

        binding.collectionRecyclerView.adapter = adapter
        binding.collectionRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL, false
        )

        val callback = SimpleItemTouchHelperCollectionCallback(adapter)
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(binding.collectionRecyclerView)

        binding.addButton.setOnClickListener {
            findNavController().navigate(
                AllCollectionFragmentDirections.actionCollectionFragmentToCreateCollectionFragment(
                    null
                )
            )
        }


        viewModel.getCollections()


        viewModel.getCollectionsLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Loading -> {}
                is ApiResponse.Failure -> {
                    val dialogFragment =
                        ErrorDialogFragment(requireContext().getString(R.string.error_get_collections))
                    dialogFragment.show(requireActivity().supportFragmentManager, "Problems")
                }
                is ApiResponse.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    adapter.notifyDataSetChanged()
                }
            }
        }

        return binding.root
    }


}