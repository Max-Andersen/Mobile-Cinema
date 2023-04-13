package com.example.mobile_cinema_lab1.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mobile_cinema_lab1.R
import com.example.mobile_cinema_lab1.SimpleItemTouchHelperCallback
import com.example.mobile_cinema_lab1.databinding.AllCollectionScreenBinding
import com.example.mobile_cinema_lab1.databinding.CollectionItemBinding
import com.example.mobile_cinema_lab1.navigationmodels.getNavigationModel
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Collection
import com.example.mobile_cinema_lab1.viewmodels.AllCollectionsViewModel

class AllCollectionFragment : Fragment() {
    private lateinit var binding: AllCollectionScreenBinding

    private val viewModel by lazy { ViewModelProvider(this)[AllCollectionsViewModel::class.java] }

    private lateinit var adapter: CollectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AllCollectionScreenBinding.inflate(inflater, container, false)

        adapter = CollectionAdapter(viewModel.userCollections)

        binding.collectionRecyclerView.adapter = adapter
        binding.collectionRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL, false
        )

        val callback = SimpleItemTouchHelperCallback(adapter)
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(binding.collectionRecyclerView)


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
                    viewModel.saveCollections(it.data)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        return binding.root
    }


    inner class CollectionAdapter(private val collections: ArrayList<Collection>) :
        RecyclerView.Adapter<CollectionItemViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CollectionItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val v = layoutInflater.inflate(R.layout.collection_item, parent, false)
            return CollectionItemViewHolder(v)
        }

        override fun getItemCount(): Int = collections.size

        override fun onBindViewHolder(holder: CollectionItemViewHolder, position: Int) {
            holder.bind(collections[position])
        }

        fun deleteCollection(position: Int) {
            collections.removeAt(position)
            notifyDataSetChanged()
            // TODO viewModel request
            Log.d("!", "SWIPED!!   $position")
        }

    }

    inner class CollectionItemViewHolder(itemView: View) : ViewHolder(itemView), View.OnClickListener {
        private val binding by viewBinding(CollectionItemBinding::bind)
        private lateinit var data: Collection

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(data: Collection) {
            this.data = data
            binding.collectionName.text = data.name

            // TODO( GET ICON WITH ID )
        }

        override fun onClick(p0: View?) {
            findNavController().navigate(AllCollectionFragmentDirections.actionCollectionFragmentToSpecificCollectionFragment(data.getNavigationModel()))
        }
    }
}