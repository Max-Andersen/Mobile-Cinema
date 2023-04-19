package com.example.mobile_cinema_lab1.ui.episode

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mobile_cinema_lab1.databinding.EpisodeScreenBinding
import com.example.mobile_cinema_lab1.datasource.network.ApiResponse

class EpisodeFragment : Fragment() {

    private lateinit var binding: EpisodeScreenBinding

    private val args: EpisodeFragmentArgs by navArgs()

    private val viewModel by lazy { ViewModelProvider(this)[EpisodeScreenViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EpisodeScreenBinding.inflate(inflater, container, false)

        viewModel.getCollections()

        val episode = args.episodeData

        val movie = args.movieData

        val player = ExoPlayer.Builder(requireContext()).build()

        binding.playerView.player = player

        val url = episode.filePath
        // Build the media item.
        val mediaItem = MediaItem.fromUri(url)
        // Set the media item to be played.
        player.setMediaItem(mediaItem)
        // Prepare the player.
        player.prepare()

        viewModel.getEpisodeTime(args.episodeData.episodeId)

        viewModel.getLiveDataForEpisodeTime().observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Loading -> {}
                is ApiResponse.Failure -> {}
                is ApiResponse.Success -> {
                    player.seekTo((it.data.timeInSeconds * 1000).toLong())
                    player.play()
                }
            }
        }

        viewModel.getLiveDataForAddToCollection().observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Loading -> {}
                is ApiResponse.Failure -> {
                    Toast.makeText(requireContext(), it.code.toString(), Toast.LENGTH_LONG).show()
                }
                is ApiResponse.Success -> {
                    Log.d("!", "SUCCESS ADD TO COLLECTION")
                }
            }
        }

        viewModel.getLiveDataForNavigationUp().observe(viewLifecycleOwner){
            if (it == true){
                viewModel.navigationSuccessful()
                findNavController().navigateUp()
            }
        }


        binding.episodeTitle.text = episode.name

        binding.movieName.text = args.movieData.name

        binding.movieYears.text = args.movieYearDuration

        binding.description.text = episode.description

        Glide.with(binding.movieImage).load(movie.poster).into(binding.movieImage)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    player.stop()
                    viewModel.saveEpisodeTime(
                        args.episodeData.episodeId,
                        (player.currentPosition / 1000).toInt(),
                        isNavigateUp = true
                    )
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding.backButton.setOnClickListener {
            callback.handleOnBackPressed()
        }

        binding.addToCollection.setOnClickListener {
            if (viewModel.collectionsLoaded) {
                showPopupMenu(binding.addToCollection)
            }
        }

        binding.discussionButton.setOnClickListener {
            player.stop()
            viewModel.saveEpisodeTime(
                args.episodeData.episodeId,
                (player.currentPosition / 1000).toInt()
            )
            findNavController().navigate(
                EpisodeFragmentDirections.actionEpisodeFragmentToChatFragment(
                    args.movieData.chatInfo
                )
            )
        }

        return binding.root
    }


    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(requireContext(), v)

        val items = mutableListOf<MenuItem>()

        viewModel.collectionList.forEach {
            popupMenu.menu.add(it.name)
        }

        popupMenu.menu.forEach {
            items.add(it)
        }


        popupMenu
            .setOnMenuItemClickListener { item ->
                viewModel.addMovieToCollection(items.indexOf(item), args.movieData.movieId)
                true
            }
        popupMenu.show()
    }

}