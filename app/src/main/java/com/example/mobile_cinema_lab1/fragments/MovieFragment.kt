package com.example.mobile_cinema_lab1.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_cinema_lab1.MainActivity
import com.example.mobile_cinema_lab1.R
import com.example.mobile_cinema_lab1.databinding.MovieScreenBinding
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Episode
import com.example.mobile_cinema_lab1.network.models.Movie
import com.example.mobile_cinema_lab1.viewmodels.MovieFrameViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MovieFragment : Fragment() {
    lateinit var binding: MovieScreenBinding

    private val viewModel by lazy { ViewModelProvider(this)[MovieFrameViewModel::class.java] }

    private lateinit var episodesAdapter: EpisodeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieScreenBinding.inflate(inflater, container, false)

        val data = requireArguments().getString("selectedMovie")

        val type: Type = object : TypeToken<Movie>() {}.type
        val movie: Movie = Gson().fromJson(data, type)

        viewModel.getEpisodesOfMovie(movie.movieId)

        episodesAdapter = EpisodeAdapter(viewModel.episodesList)

        binding.episodesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.imagesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        binding.imagesRecyclerView.adapter = MovieAdapter(movie)

        val itemDecoration = (requireActivity() as MainActivity).getMarginItemDecoration(16)

        binding.imagesRecyclerView.addItemDecoration(itemDecoration)


        binding.episodesRecyclerView.adapter = episodesAdapter
        binding.episodesRecyclerView.addItemDecoration(itemDecoration)


        Glide.with(requireActivity()).load(movie.poster).into(binding.backgroundImagePromotedMovie)

        binding.descriptionText.text = movie.description
        binding.ageLimit.text = movie.age
        binding.promotedMovieName.text = movie.name

        binding.flow.setup(binding.data, movie.tags)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }


        viewModel.getLiveDataForEpisodes().observe(viewLifecycleOwner) {
            when (it) {
                ApiResponse.Loading -> {

                }
                is ApiResponse.Failure -> {
                    Log.d("!", "Fail")
                }
                is ApiResponse.Success -> {
                    binding.episodesProgressBar.visibility = View.INVISIBLE
                    viewModel.episodesList.clear()
                    it.data.forEach { movie ->
                        viewModel.episodesList.add(movie)
                    }
                    episodesAdapter.notifyDataSetChanged()
                }
            }

        }


        (requireActivity() as MainActivity).hideBottomNavigation()

        return binding.root
    }


    private inner class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = itemView.findViewById<ImageView>(R.id.image)
        fun bind(data: String) {
            Glide.with(requireActivity()).load(data).into(imageView)
        }
    }

    private inner class MovieAdapter(var movie: Movie) :
        RecyclerView.Adapter<ImageHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
            val view =
                layoutInflater.inflate(
                    R.layout.horizontal_image_item_for_recyclerview,
                    parent,
                    false
                )
            return ImageHolder(view)
        }

        override fun getItemCount(): Int {
            return movie.imageUrls.size
        }

        override fun onBindViewHolder(holder: ImageHolder, position: Int) {
            val movie = movie.imageUrls[position]
            holder.bind(movie)
        }
    }


    private inner class EpisodeHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var data: Episode
        private val imageView = itemView.findViewById<ImageView>(R.id.episodeImage)
        fun bind(data: Episode) {
            this.data = data
            Glide.with(requireActivity()).load(data.preview).into(imageView)
            itemView.findViewById<TextView>(R.id.episodeDescription).text = data.description
            itemView.findViewById<TextView>(R.id.episodeName).text = data.name
            itemView.findViewById<TextView>(R.id.episodeYear).text = data.year.toString()
        }
    }

    private inner class EpisodeAdapter(var movie: List<Episode>) :
        RecyclerView.Adapter<EpisodeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeHolder {
            val view =
                layoutInflater.inflate(
                    R.layout.episode_card,
                    parent,
                    false
                )
            return EpisodeHolder(view)
        }

        override fun getItemCount(): Int {
            return movie.size
        }

        override fun onBindViewHolder(holder: EpisodeHolder, position: Int) {
            val movie = movie[position]
            holder.bind(movie)
        }
    }




}