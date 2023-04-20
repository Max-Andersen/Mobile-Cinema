package com.example.mobilecinemalab.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.mobilecinemalab.R
import com.example.mobilecinemalab.databinding.MainFrameBinding
import com.example.mobilecinemalab.databinding.VerticalImageItemForRecyclerviewBinding
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.Movie
import com.example.mobilecinemalab.forapplication.MainActivity
import com.example.mobilecinemalab.forapplication.errorhandling.ErrorDialogFragment
import com.example.mobilecinemalab.navigationmodels.getNavigationModel


class MainFragment : Fragment() {
    private lateinit var binding: MainFrameBinding

    private val viewModel by lazy { ViewModelProvider(this)[MainFragmentViewModel::class.java] }

    private lateinit var inTrendAdapter: MovieAdapter
    private lateinit var newMovieAdapter: MovieAdapter
    private lateinit var forYouAdapter: MovieAdapter

    private lateinit var lastWatchedMovie: Movie

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFrameBinding.inflate(inflater, container, false)
        binding.watchPromotedMovie.setOnClickListener {
            val dialogFragment =
                ErrorDialogFragment(requireContext().getString(R.string.error_dont_touch_here))
            activity?.let { it1 -> dialogFragment.show(it1.supportFragmentManager, "Problems") }
        }

        inTrendAdapter = MovieAdapter(viewModel.inTrendMovies)
        newMovieAdapter = MovieAdapter(viewModel.newMovies)
        forYouAdapter = MovieAdapter(viewModel.moviesForYou)

        binding.inTrendRecyclerView.adapter = inTrendAdapter
        binding.newMoviesRecyclerView.adapter = newMovieAdapter
        binding.moviesForYouRecyclerView.adapter = forYouAdapter

        binding.inTrendRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.newMoviesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.moviesForYouRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val itemDecoration = (requireActivity() as MainActivity).getMarginItemDecoration(16)

        binding.inTrendRecyclerView.addItemDecoration(itemDecoration)
        binding.newMoviesRecyclerView.addItemDecoration(itemDecoration)
        binding.moviesForYouRecyclerView.addItemDecoration(itemDecoration)

        binding.lastWatchedMovieButton.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToMovieFragment(
                    lastWatchedMovie.getNavigationModel()
                )
            )
        }

        binding.selectPreferencesButton.setOnClickListener {
            val dialogFragment =
                ErrorDialogFragment(requireContext().getString(R.string.error_dont_touch_here))
            dialogFragment.show(requireActivity().supportFragmentManager, "Problems")
        }

        viewModel.getMovies()
        updateUi()

        binding.forYouGroup.visibility = View.GONE

        (requireActivity() as MainActivity).showBottomNavigation()

        return binding.root
    }

    private fun updateUi() {
        viewModel.getLiveDataForInTrendMovies().observe(requireActivity()) {
            when (it) {
                ApiResponse.Loading -> {

                }
                is ApiResponse.Failure -> {
                    val errorDialog =
                        ErrorDialogFragment(requireContext().getString(R.string.error_get_in_trend_movies))
                    errorDialog.show(requireActivity().supportFragmentManager, "Problems")
                }
                is ApiResponse.Success -> {
                    viewModel.itemLoaded()
                    if (it.data.isNotEmpty()) {
                        viewModel.saveInTrendMovies(it.data)
                        binding.inTrendGroup.visibility = View.VISIBLE
                        inTrendAdapter.notifyDataSetChanged()
                    }

                }
            }
        }

        viewModel.getLiveDataForMoviesForYou().observe(requireActivity()) {
            when (it) {
                ApiResponse.Loading -> {

                }
                is ApiResponse.Failure -> {
                    val errorDialog =
                        ErrorDialogFragment(requireContext().getString(R.string.error_get_for_you_movies))
                    errorDialog.show(requireActivity().supportFragmentManager, "Problems")
                }
                is ApiResponse.Success -> {
                    viewModel.itemLoaded()
                    if (it.data.isNotEmpty()) {
                        viewModel.saveForYouMovies(it.data)
                        binding.forYouGroup.visibility = View.VISIBLE
                        forYouAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        viewModel.getLiveDataForNewMovies().observe(requireActivity()) {
            when (it) {
                ApiResponse.Loading -> {

                }
                is ApiResponse.Failure -> {
                    val errorDialog =
                        ErrorDialogFragment(requireContext().getString(R.string.error_get_new_movies))
                    errorDialog.show(requireActivity().supportFragmentManager, "Problems")
                }
                is ApiResponse.Success -> {
                    viewModel.itemLoaded()
                    if (it.data.isNotEmpty()) {
                        viewModel.saveNewMovies(it.data)
                        binding.newMoviesGroup.visibility = View.VISIBLE
                        newMovieAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        viewModel.getLiveDataForCoverImage().observe(viewLifecycleOwner) {
            when (it) {
                ApiResponse.Loading -> {

                }
                is ApiResponse.Failure -> {
                    val errorDialog =
                        ErrorDialogFragment(requireContext().getString(R.string.error_get_cover_image))
                    errorDialog.show(requireActivity().supportFragmentManager, "Problems")
                }
                is ApiResponse.Success -> {
                    viewModel.itemLoaded()
                    Glide.with(requireActivity()).load(it.data.backgroundImage)
                        .into(binding.backgroundImagePromotedMovie)
                    Glide.with(requireActivity()).load(it.data.foregroundImage)
                        .into(binding.foregroundImagePromotedMovie)
                }
            }
        }

        viewModel.getLiveDataForYouWatchedMovie().observe(viewLifecycleOwner) {
            when (it) {
                ApiResponse.Loading -> {

                }
                is ApiResponse.Failure -> {
                    val errorDialog =
                        ErrorDialogFragment(requireContext().getString(R.string.error_get_for_you_movies))
                    errorDialog.show(requireActivity().supportFragmentManager, "Problems")
                }
                is ApiResponse.Success -> {
                    viewModel.itemLoaded()
                    if (it.data.isNotEmpty()) {
                        lastWatchedMovie = it.data.first()

                        Glide.with(requireActivity()).load(lastWatchedMovie.imageUrls.first())
                            .into(binding.lastWatchedMovie)
                        binding.lastWatchedMovieTitle.text = lastWatchedMovie.name
                        binding.lastWatchedMovieGroup.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewModel.getLiveDataForLoadedItems().observe(viewLifecycleOwner) {
            if (it >= 5) {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private inner class MovieHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private lateinit var data: Movie

        private val binding by viewBinding(VerticalImageItemForRecyclerviewBinding::bind)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(data: Movie) {
            this.data = data
            Glide.with(requireActivity()).load(data.poster).into(binding.collectionImage)
        }

        override fun onClick(p0: View?) {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToMovieFragment(
                    data.getNavigationModel()
                )
            )
        }
    }

    private inner class MovieAdapter(var movies: List<Movie>) :
        RecyclerView.Adapter<MovieHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
            val view =
                layoutInflater.inflate(R.layout.vertical_image_item_for_recyclerview, parent, false)
            return MovieHolder(view)
        }

        override fun getItemCount(): Int {
            return movies.size
        }

        override fun onBindViewHolder(holder: MovieHolder, position: Int) {
            val movie = movies[position]
            holder.bind(movie)
        }

    }
}