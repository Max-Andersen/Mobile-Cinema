package com.example.mobile_cinema_lab1.fragments

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.mobile_cinema_lab1.MainActivity
import com.example.mobile_cinema_lab1.R
import com.example.mobile_cinema_lab1.SimpleItemTouchHelperCollectionCallback
import com.example.mobile_cinema_lab1.SwipeAdapter
import com.example.mobile_cinema_lab1.databinding.CollectionMovieItemBinding
import com.example.mobile_cinema_lab1.databinding.SpecificCollectionScreenBinding
import com.example.mobile_cinema_lab1.navigationmodels.getNavigationModel
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Movie
import com.example.mobile_cinema_lab1.viewmodels.SpecificCollectionViewModel

class SpecificCollectionFragment : Fragment() {
    private lateinit var binding: SpecificCollectionScreenBinding

    private val viewModel by lazy { ViewModelProvider(this)[SpecificCollectionViewModel::class.java] }

    private val args: SpecificCollectionFragmentArgs by navArgs()

    private lateinit var adapter: CollectionMovieAdapter


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
                is ApiResponse.Failure -> {} // FAIL TO GET DATA
                else -> {}
            }
        }

        adapter = CollectionMovieAdapter(viewModel.movies)

        binding.collectionItemsRecyclerView.adapter = adapter
        binding.collectionItemsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        val callback = SimpleItemTouchHelperCollectionCallback(adapter)
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(binding.collectionItemsRecyclerView)

        return binding.root
    }

    private inner class CollectionMovieAdapter(private var movies: ArrayList<Movie>) :
        RecyclerView.Adapter<CollectionMovieItemViewHolder>(), SwipeAdapter {
        override fun getItemCount(): Int = movies.size

        override fun getItemViewType(position: Int): Int {
            return 1
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CollectionMovieItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val v = layoutInflater.inflate(R.layout.collection_movie_item, parent, false)
            return CollectionMovieItemViewHolder(v)
        }

        override fun onBindViewHolder(holder: CollectionMovieItemViewHolder, position: Int) {
            val item = movies[position]
            holder.bind(item)
        }

        override fun deleteCollection(position: Int) {
            viewModel.deleteMovieFromCollection(movies[position])
            notifyDataSetChanged()
        }
    }

    private inner class CollectionMovieItemViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val binding by viewBinding(CollectionMovieItemBinding::bind)
        private lateinit var movieData: Movie

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(data: Movie) {
            movieData = data
            binding.movieDescription.text = data.description
            Glide.with(itemView).load(data.poster).into(binding.movieImage)
            binding.movieName.text = data.name
        }

        override fun onClick(p0: View?) {
            findNavController().navigate(SpecificCollectionFragmentDirections.actionSpecificCollectionFragmentToMovieFragment(movieData.getNavigationModel()))
        }
    }

}