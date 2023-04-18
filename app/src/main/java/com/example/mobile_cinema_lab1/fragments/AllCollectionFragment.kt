package com.example.mobile_cinema_lab1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mobile_cinema_lab1.*
import com.example.mobile_cinema_lab1.databinding.AllCollectionScreenBinding
import com.example.mobile_cinema_lab1.databinding.CollectionItemBinding
import com.example.mobile_cinema_lab1.network.ApiResponse
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

        (requireActivity() as MainActivity).showBottomNavigation()

        adapter = CollectionAdapter(viewModel.userCollections)

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


    private inner class CollectionAdapter(private val collections: MutableList<CollectionUIModel>) :
        RecyclerView.Adapter<CollectionItemViewHolder>(), SwipeAdapter {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CollectionItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val v = layoutInflater.inflate(R.layout.collection_item, parent, false)
            return CollectionItemViewHolder(v)
        }

        override fun getItemViewType(position: Int): Int {
            return if (position == 0) 0 else 1 // 0 equals Favourite collection
        }

        override fun getItemCount(): Int = collections.size

        override fun onBindViewHolder(holder: CollectionItemViewHolder, position: Int) {
            holder.bind(collections[position])
        }

        override fun deleteCollection(position: Int) {
            viewModel.deleteCollection(collections[position].collectionId)
            collections.removeAt(position)
            notifyDataSetChanged()
        }
    }

    private inner class CollectionItemViewHolder(itemView: View) : ViewHolder(itemView),
        View.OnClickListener {
        private val binding by viewBinding(CollectionItemBinding::bind)
        private lateinit var data: CollectionUIModel
        private val icons = mapOf(
            "1" to R.drawable.group_1,
            "2" to R.drawable.group_2,
            "3" to R.drawable.group_3,
            "4" to R.drawable.group_4,
            "5" to R.drawable.group_5,
            "6" to R.drawable.group_6,
            "7" to R.drawable.group_7,
            "8" to R.drawable.group_8,
            "9" to R.drawable.group_9,
            "10" to R.drawable.group_10,
            "11" to R.drawable.group_11,
            "12" to R.drawable.group_12,
            "13" to R.drawable.group_13,
            "14" to R.drawable.group_14,
            "15" to R.drawable.group_15,
            "16" to R.drawable.group_16,
            "17" to R.drawable.group_17,
            "18" to R.drawable.group_18,
            "19" to R.drawable.group_19,
            "20" to R.drawable.group_20,
            "21" to R.drawable.group_21,
            "22" to R.drawable.group_22,
            "23" to R.drawable.group_23,
            "24" to R.drawable.group_24,
            "25" to R.drawable.group_25,
            "26" to R.drawable.group_26,
            "27" to R.drawable.group_27,
            "28" to R.drawable.group_28,
            "29" to R.drawable.group_29,
            "30" to R.drawable.group_30,
            "31" to R.drawable.group_31,
            "32" to R.drawable.group_32,
            "33" to R.drawable.group_33,
            "34" to R.drawable.group_34,
            "35" to R.drawable.group_35,
            "36" to R.drawable.group_36,
        )

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(data: CollectionUIModel) {
            this.data = data
            binding.collectionName.text = data.name
            if (this.data.iconId.isBlank()) this.data.iconId = "1"

            binding.collectionImage.setImageResource(CollectionIconsEnum().icons[this.data.iconId]!!)
        }

        override fun onClick(p0: View?) {
            itemView.findNavController().navigate(
                AllCollectionFragmentDirections.actionCollectionFragmentToSpecificCollectionFragment(
                    data.getNavigationModel(),
                    data.iconId
                )
            )
        }
    }

}