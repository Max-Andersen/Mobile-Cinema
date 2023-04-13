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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_cinema_lab1.MainActivity
import com.example.mobile_cinema_lab1.R
import com.example.mobile_cinema_lab1.databinding.MovieScreenBinding
import com.example.mobile_cinema_lab1.navigationmodels.getNavigationModel
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Episode
import com.example.mobile_cinema_lab1.viewmodels.MovieFrameViewModel

class MovieFragment : Fragment() {
    lateinit var binding: MovieScreenBinding

    private val viewModel by lazy { ViewModelProvider(this)[MovieFrameViewModel::class.java] }

    private lateinit var episodesAdapter: EpisodeAdapter

    private val args: MovieFragmentArgs by navArgs()

    private lateinit var movie: com.example.mobile_cinema_lab1.navigationmodels.Movie

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieScreenBinding.inflate(inflater, container, false)

        movie = args.selectedMovie

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

        when (movie.age) {
            "18+" -> binding.ageLimit.setTextColor(requireActivity().getColor(R.color.orange))
            "16+" -> binding.ageLimit.setTextColor(requireActivity().getColor(R.color.sixteen_years))
            "12+" -> binding.ageLimit.setTextColor(requireActivity().getColor(R.color.twelve_years))
            "6+" -> binding.ageLimit.setTextColor(requireActivity().getColor(R.color.six_years))
            else -> binding.ageLimit.setTextColor(requireActivity().getColor(R.color.white))
        }

        binding.promotedMovieName.text = movie.name

        binding.flow.setup(binding.data, movie.tags)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.chatButton.setOnClickListener {
            findNavController().navigate(MovieFragmentDirections.actionMovieFragmentToChatFragment(movie.chatInfo))
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
        private val imageView = itemView.findViewById<ImageView>(R.id.collectionImage)
        fun bind(data: String) {
            Glide.with(requireActivity()).load(data).into(imageView)
        }
    }

    private inner class MovieAdapter(var movie: com.example.mobile_cinema_lab1.navigationmodels.Movie) :
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


    private inner class EpisodeHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var data: Episode
        private val imageView = itemView.findViewById<ImageView>(R.id.episodeImage)
        fun bind(data: Episode) {
            this.data = data
            Glide.with(requireActivity()).load(data.preview).into(imageView)
            itemView.findViewById<TextView>(R.id.episodeDescription).text = data.description
            itemView.findViewById<TextView>(R.id.episodeName).text = data.name
            itemView.findViewById<TextView>(R.id.episodeYear).text = data.year.toString()
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            findNavController().navigate(MovieFragmentDirections.actionMovieFragmentToEpisodeFragment(data.getNavigationModel(), movie.name, movie.movieId))
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