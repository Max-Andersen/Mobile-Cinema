package com.example.mobilecinemalab.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobilecinemalab.forapplication.MainActivity
import com.example.mobilecinemalab.R
import com.example.mobilecinemalab.databinding.MovieScreenBinding
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.Episode
import com.example.mobilecinemalab.forapplication.errorhandling.ErrorDialogFragment
import com.example.mobilecinemalab.ui._custombehavior.MarginItemDecoration
import com.example.mobilecinemalab.ui._custombehavior.getYearDurationOfMovie
import com.example.mobilecinemalab.ui.movie.episodes.EpisodeAdapter
import com.example.mobilecinemalab.ui.movie.images.MovieImageAdapter
import com.google.android.material.chip.Chip

class MovieFragment : Fragment() {
    lateinit var binding: MovieScreenBinding

    private val viewModel by lazy { ViewModelProvider(this)[MovieFrameViewModel::class.java] }

    private lateinit var episodesAdapter: EpisodeAdapter

    private val args: MovieFragmentArgs by navArgs()

    private lateinit var movie: com.example.mobilecinemalab.navigationmodels.Movie

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieScreenBinding.inflate(inflater, container, false)

        movie = args.selectedMovie

        viewModel.getEpisodesOfMovie(movie.movieId)

        episodesAdapter = EpisodeAdapter(viewModel.episodesList, movie){
            getYearDurationOfMovie(viewModel.episodesList)
        }

        binding.episodesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.imagesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        binding.imagesRecyclerView.adapter = MovieImageAdapter(movie)

        val itemDecoration = MarginItemDecoration(16)

        binding.imagesRecyclerView.addItemDecoration(itemDecoration)


        binding.episodesRecyclerView.adapter = episodesAdapter
        binding.episodesRecyclerView.addItemDecoration(itemDecoration)


        Glide.with(requireActivity()).load(movie.poster).into(binding.backgroundImagePromotedMovie)

        binding.descriptionText.text = movie.description
        binding.ageLimit.text = movie.age

        when (movie.age) {
            "18+" -> binding.ageLimit.setTextColor(requireActivity().getColor(R.color.orange))
            "16+" -> binding.ageLimit.setTextColor(requireActivity().getColor(R.color.sixteen_years))
            "12+" -> binding.ageLimit.setTextColor(requireActivity().getColor(R.color.twelve_years))
            "6+" -> binding.ageLimit.setTextColor(requireActivity().getColor(R.color.six_years))
            else -> binding.ageLimit.setTextColor(requireActivity().getColor(R.color.white))
        }

        binding.promotedMovieName.text = movie.name

        fillTags()

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.chatButton.setOnClickListener {
            findNavController().navigate(
                MovieFragmentDirections.actionMovieFragmentToChatFragment(
                    movie.chatInfo
                )
            )
        }


        viewModel.getLiveDataForEpisodes().observe(viewLifecycleOwner) {
            when (it) {
                ApiResponse.Loading -> {

                }
                is ApiResponse.Failure -> {
                    val errorDialog = ErrorDialogFragment(requireContext().getString(R.string.error_get_episodes))
                    errorDialog.show(requireActivity().supportFragmentManager, "Problems")
                }
                is ApiResponse.Success -> {
                    binding.episodesProgressBar.visibility = View.INVISIBLE
                    viewModel.saveEpisodes(it.data)
                    episodesAdapter.notifyDataSetChanged()
                }
            }

        }


        (requireActivity() as MainActivity).hideBottomNavigation()

        return binding.root
    }

    private fun fillTags() {
        movie.tags.forEach {
            val chip = inflate(
                context,
                R.layout.tag_chip,
                null
            ) as Chip
            chip.text = it.tagName

            binding.chipGroup.addView(chip)
        }
    }

}