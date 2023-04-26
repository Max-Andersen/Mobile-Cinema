package com.example.mobilecinemalab.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mobilecinemalab.R
import com.example.mobilecinemalab.databinding.MainFrameBinding
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.EpisodeShort
import com.example.mobilecinemalab.datasource.network.models.Movie
import com.example.mobilecinemalab.forapplication.MainActivity
import com.example.mobilecinemalab.forapplication.errorhandling.ErrorDialogFragment
import com.example.mobilecinemalab.navigationmodels.Episode
import com.example.mobilecinemalab.navigationmodels.getNavigationModel
import com.example.mobilecinemalab.ui._custombehavior.MarginItemDecoration
import com.example.mobilecinemalab.navigationmodels.Movie as MovieNavigationModel


class MainFragment : Fragment() {
    private lateinit var binding: MainFrameBinding

    private val viewModel by lazy { ViewModelProvider(this)[MainFragmentViewModel::class.java] }

    private lateinit var inTrendAdapter: MovieAdapter
    private lateinit var newMovieAdapter: MovieAdapter
    private lateinit var forYouAdapter: MovieAdapter

    private lateinit var lastWatchedMovie: MovieNavigationModel
    private lateinit var lastWatchedEpisode: Episode
    private lateinit var lastWatchedMovieYearDuration: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFrameBinding.inflate(inflater, container, false)

        binding.watchPromotedMovie.setOnClickListener {
            val dialogFragment =
                ErrorDialogFragment(requireContext().getString(R.string.error_dont_touch_here))
            dialogFragment.show(requireActivity().supportFragmentManager, getString(R.string.problems))
        }

        binding.selectPreferencesButton.setOnClickListener {
            val dialogFragment =
                ErrorDialogFragment(requireContext().getString(R.string.error_dont_touch_here))
            dialogFragment.show(requireActivity().supportFragmentManager, getString(R.string.problems))
        }


        inTrendAdapter = MovieAdapter(viewModel.inTrendMovies, "vertical")
        newMovieAdapter = MovieAdapter(viewModel.newMovies, "horizontal")
        forYouAdapter = MovieAdapter(viewModel.moviesForYou, "vertical")

        binding.inTrendRecyclerView.adapter = inTrendAdapter
        binding.newMoviesRecyclerView.adapter = newMovieAdapter
        binding.moviesForYouRecyclerView.adapter = forYouAdapter

        binding.inTrendRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.newMoviesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.moviesForYouRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val itemDecoration = MarginItemDecoration(16)

        binding.inTrendRecyclerView.addItemDecoration(itemDecoration)
        binding.newMoviesRecyclerView.addItemDecoration(itemDecoration)
        binding.moviesForYouRecyclerView.addItemDecoration(itemDecoration)

        binding.lastWatchedMovieButton.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToEpisodeFragment(
                    lastWatchedEpisode,
                    lastWatchedMovie,
                    lastWatchedMovieYearDuration
                )
            )
        }

        viewModel.getMovies()
        updateUi()

        (requireActivity() as MainActivity).showBottomNavigation()

        return binding.root
    }

    private fun updateUi() {

        viewModel.getLiveDataForInTrendMovies().observe(requireActivity()) {
            when (it) {
                ApiResponse.Loading -> {  }
                is ApiResponse.Failure -> {
                    val errorDialog =
                        ErrorDialogFragment(requireContext().getString(R.string.error_get_in_trend_movies))
                    errorDialog.show(requireActivity().supportFragmentManager, getString(R.string.problems))
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
                ApiResponse.Loading -> {  }
                is ApiResponse.Failure -> {
                    val errorDialog =
                        ErrorDialogFragment(requireContext().getString(R.string.error_get_for_you_movies))
                    errorDialog.show(requireActivity().supportFragmentManager, getString(R.string.problems))
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
                ApiResponse.Loading -> {  }
                is ApiResponse.Failure -> {
                    val errorDialog =
                        ErrorDialogFragment(requireContext().getString(R.string.error_get_new_movies))
                    errorDialog.show(requireActivity().supportFragmentManager, getString(R.string.problems))
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
                ApiResponse.Loading -> {  }
                is ApiResponse.Failure -> {
                    val errorDialog =
                        ErrorDialogFragment(requireContext().getString(R.string.error_get_cover_image))
                    errorDialog.show(requireActivity().supportFragmentManager, getString(R.string.problems))
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

        viewModel.getLiveDataForHistory().combineWith(viewModel.getLiveDataForYouWatchedMovie())
            .observe(viewLifecycleOwner) { lastPairState ->

                if (lastPairState.first is ApiResponse.Success && lastPairState.second is ApiResponse.Success) {
                    val lastEpisode =
                        (lastPairState.first as ApiResponse.Success<List<EpisodeShort>>).data.firstOrNull()

                    if (lastEpisode != null) {
                        val movie =
                            (lastPairState.second as ApiResponse.Success<List<Movie>>).data.find { it.movieId == lastEpisode.movieId }

                        lastWatchedMovie = movie!!.getNavigationModel()
                        lastWatchedEpisode = lastEpisode.getNavigationModel()
                        lastWatchedMovieYearDuration = ""

                        Glide.with(requireActivity()).load(movie.imageUrls.first())
                            .into(binding.lastWatchedMovie)
                        binding.lastWatchedMovieTitle.text = lastWatchedMovie.name
                        binding.lastWatchedMovieGroup.visibility = View.VISIBLE

                        binding.lastWatchedMovieButton.isActivated = true
                    } else {
                        binding.lastWatchedMovieButton.isActivated = false
                    }

                }

                if (lastPairState.first is ApiResponse.Failure || lastPairState.second is ApiResponse.Failure) {
                    val errorDialog =
                        ErrorDialogFragment(requireContext().getString(R.string.error_get_last_watched_movie))
                    errorDialog.show(requireActivity().supportFragmentManager, getString(R.string.problems))
                }
            }


        viewModel.getLiveDataForLoadedItems().observe(viewLifecycleOwner) {
            if (it >= 4) {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun <T, S> LiveData<T?>.combineWith(other: LiveData<S?>): LiveData<Pair<T?, S?>> =
        MediatorLiveData<Pair<T?, S?>>().apply {
            addSource(this@combineWith) { value = Pair(it, other.value) }
            addSource(other) { value = Pair(this@combineWith.value, it) }
        }

}