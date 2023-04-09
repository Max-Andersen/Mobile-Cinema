package com.example.mobile_cinema_lab1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mobile_cinema_lab1.databinding.EpisodeScreenBinding

class EpisodeFragment: Fragment() {

    private lateinit var binding: EpisodeScreenBinding

    private val args: EpisodeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EpisodeScreenBinding.inflate(inflater, container, false)

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

        return binding.root
    }

}