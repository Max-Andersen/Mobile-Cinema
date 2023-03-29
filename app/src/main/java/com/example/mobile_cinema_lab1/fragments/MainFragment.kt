package com.example.mobile_cinema_lab1.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_cinema_lab1.MainActivity
import com.example.mobile_cinema_lab1.R
import com.example.mobile_cinema_lab1.databinding.MainFrameBinding
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Movie
import com.example.mobile_cinema_lab1.viewmodels.MainFragmentViewModel


class MainFragment : Fragment() {
    private lateinit var binding: MainFrameBinding

    private val viewModel by lazy { ViewModelProvider(this)[MainFragmentViewModel::class.java] }

    private lateinit var inTrendAdapter: MovieAdapter
    private lateinit var newMovieAdapter: MovieAdapter
    private lateinit var forYouAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFrameBinding.inflate(inflater, container, false)
        binding.watchPromotedMovie.setOnClickListener {
            val dialogFragment = ErrorDialogFragment("Не надо сюда нажимать!!")
            activity?.let { it1 -> dialogFragment.show(it1.supportFragmentManager, "Problems") }
        }

        inTrendAdapter = MovieAdapter(viewModel.inTrendMovies)
        newMovieAdapter = MovieAdapter(viewModel.newMovies)
        forYouAdapter = MovieAdapter(viewModel.moviesForYou)

        binding.inTrendRecyclerView.adapter = inTrendAdapter
        binding.newMoviesRecyclerView.adapter = newMovieAdapter
        binding.moviesForYouRecyclerView.adapter = forYouAdapter

        binding.inTrendRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.newMoviesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.moviesForYouRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.getMovies()
        updateUi()

        (requireActivity() as MainActivity).showBottomNavigation()

        return binding.root
    }

    private fun updateUi() {
        viewModel.getLiveDataForInTrendMovies().observe(requireActivity()) {
            when (it) {
                ApiResponse.Loading -> {}
                is ApiResponse.Failure -> {
                    Log.d("!", "Fail")
                }
                is ApiResponse.Success -> {
                    viewModel.inTrendMovies = it.data as ArrayList<Movie>
                    inTrendAdapter.notifyDataSetChanged()
                }
            }
        }
        viewModel.getLiveDataForMoviesForYou().observe(requireActivity()) {
            when (it) {
                ApiResponse.Loading -> {}
                is ApiResponse.Failure -> {
                    Log.d("!", "Fail")
                }
                is ApiResponse.Success -> {
                    viewModel.moviesForYou = it.data as ArrayList<Movie>
                    forYouAdapter.notifyDataSetChanged()
                }
            }
        }
        viewModel.getLiveDataForNewMovies().observe(requireActivity()) {
            when (it) {
                ApiResponse.Loading -> {}
                is ApiResponse.Failure -> {
                    Log.d("!", "Fail")
                }
                is ApiResponse.Success -> {

                    viewModel.newMovies.clear()
                    it.data.forEach { movie ->
                        viewModel.newMovies.add(movie)
                    }

                    newMovieAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private inner class MovieHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var data: Movie
        private val imageView = itemView.findViewById<ImageView>(R.id.image)
        fun bind(data: Movie) {
            this.data = data
            Glide.with(requireActivity()).load(data.poster).into(imageView)
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