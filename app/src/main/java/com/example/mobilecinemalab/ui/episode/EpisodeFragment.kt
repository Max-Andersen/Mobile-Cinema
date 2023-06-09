package com.example.mobilecinemalab.ui.episode

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mobilecinemalab.R
import com.example.mobilecinemalab.databinding.EpisodeScreenBinding
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.forapplication.errorhandling.ErrorDialogFragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem.fromUri
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector


class EpisodeFragment : Fragment() {

    private lateinit var binding: EpisodeScreenBinding

    private val args: EpisodeFragmentArgs by navArgs()

    private val viewModel by lazy { ViewModelProvider(this)[EpisodeScreenViewModel::class.java] }

    private lateinit var player: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EpisodeScreenBinding.inflate(inflater, container, false)

        viewModel.getCollections(args.movieData.movieId)

        val episode = args.episodeData
        val movie = args.movieData

        initPlayer()

        viewModel.getEpisodeTime(args.episodeData.episodeId)

        viewModel.getLiveDataForEpisodeTime().observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Loading -> {}
                is ApiResponse.Failure -> {
                    val errorDialog =
                        ErrorDialogFragment(requireContext().getString(R.string.error_get_episode_time))
                    errorDialog.show(requireActivity().supportFragmentManager, getString(R.string.problems))
                }
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
                    Toast.makeText(requireContext(), it.code, Toast.LENGTH_LONG).show()
                }
                is ApiResponse.Success -> {
                    Log.d("!", "SUCCESS ADD TO COLLECTION")
                }
            }
        }

        viewModel.getLiveDataForNavigationUp().observe(viewLifecycleOwner) {
            if (it == true) {
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

        viewModel.getLiveDataForLikeState().observe(viewLifecycleOwner) {
            binding.likeButton.visibility = View.INVISIBLE

            Log.d("!", viewModel.isFavouriteLoaded.toString())

            if (viewModel.isFavouriteLoaded) {     // first state, need only image without animation
                viewModel.isFavouriteLoaded = false
                binding.likeButton.visibility = View.VISIBLE
                if (it) {
                    binding.likeButton.setImageResource(R.drawable.full_heart)
                } else {
                    binding.likeButton.setImageResource(R.drawable.empty_heart)
                }
            } else{
                when (it) {
                    true -> {
                        binding.unlikeAnimation.visibility = View.INVISIBLE
                        binding.likeAnimation.visibility = View.VISIBLE
                        binding.likeAnimation.playAnimation()
                    }
                    false -> {
                        binding.likeAnimation.visibility = View.INVISIBLE
                        binding.unlikeAnimation.visibility = View.VISIBLE
                        binding.unlikeAnimation.playAnimation()
                    }
                }
            }

        }

        binding.backButton.setOnClickListener {
            callback.handleOnBackPressed()
        }

        binding.addToCollection.setOnClickListener {
            if (viewModel.collectionsLoaded) {
                showPopupMenu(binding.addToCollection)
            }
        }

        binding.likeAnimation.addAnimatorUpdateListener {
            if (it.animatedValue as Float == 0.98019606f) {
                binding.likeAnimation.visibility = View.INVISIBLE
                binding.likeButton.visibility = View.VISIBLE
                binding.likeButton.setImageResource(R.drawable.full_heart)
            }
        }

        binding.unlikeAnimation.addAnimatorUpdateListener {
            if (it.animatedValue as Float == 0.9869719f) {
                binding.unlikeAnimation.visibility = View.INVISIBLE
                binding.likeButton.visibility = View.VISIBLE
                binding.likeButton.setImageResource(R.drawable.empty_heart)
            }
        }

        binding.likeButton.setOnClickListener {
            changeLikeState()
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

    private fun initPlayer() {
        player = ExoPlayer.Builder(requireContext()).build()
        player.playWhenReady = true

        binding.playerView.setOnClickListener {
            if (player.isPlaying) player.pause()
            else player.play()
        }

        binding.playerView.player = player

        val url = args.episodeData.filePath
        val mediaItem = fromUri(url)

        player.setMediaItem(mediaItem)
        player.prepare()
    }

    private fun changeLikeState() {
        viewModel.changeState()
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