package com.example.mobile_cinema_lab1.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mobile_cinema_lab1.R
import com.example.mobile_cinema_lab1.databinding.EpisodeScreenBinding
import com.example.mobile_cinema_lab1.viewmodels.EpisodeScreenViewModel


class EpisodeFragment: Fragment() {

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

        val player = ExoPlayer.Builder(requireContext()).build()

        binding.playerView.player = player

        val url = episode.filePath
                // Build the media item.
        val mediaItem = MediaItem.fromUri(url)
                // Set the media item to be played.
        player.setMediaItem(mediaItem)
                // Prepare the player.
        player.prepare()
                // Start the playback.
        player.play()

        binding.episodeTitle.text = episode.name

        binding.movieName.text = args.movieName

        binding.movieYears.text = episode.year.toString()

        binding.description.text = episode.description

        Glide.with(binding.movieImage).load(episode.preview).into(binding.movieImage)

        binding.backButton.setOnClickListener {
            // TODO ( stop player & send time )
            findNavController().navigateUp()
        }

        binding.addToCollection.setOnClickListener {
            if (viewModel.collectionsLoaded){
                showPopupMenu(binding.addToCollection)
            }
        }

        return binding.root
    }


    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(requireContext(), v)

        viewModel.collectionList.forEach {
            popupMenu.menu.add(it.name)
        }


        popupMenu
            .setOnMenuItemClickListener { item ->
                viewModel.addMovieToCollection(item.order, args.movieId)
                false
            }
        popupMenu.show()
    }


}