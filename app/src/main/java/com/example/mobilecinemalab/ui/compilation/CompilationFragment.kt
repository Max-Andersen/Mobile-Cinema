package com.example.mobilecinemalab.ui.compilation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobilecinemalab.R
import com.example.mobilecinemalab.forapplication.MainActivity
import com.example.mobilecinemalab.databinding.CompilationScreenBinding
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.forapplication.errorhandling.ErrorDialogFragment
import com.example.mobilecinemalab.navigationmodels.getNavigationModel
import com.yuyakaido.android.cardstackview.*

class CompilationFragment : Fragment(), CardStackListener {
    private lateinit var binding: CompilationScreenBinding

    private val viewModel by lazy { ViewModelProvider(this)[CompilationViewModel::class.java] }

    private lateinit var cardStackView: CardStackView
    private lateinit var manager: CardStackLayoutManager

    private val adapter by lazy { CardStackAdapter(viewModel.compilationMovies) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CompilationScreenBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).showBottomNavigation()

        viewModel.getCompilation()

        iniStackView()

        binding.likeLayer.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Slow.duration)
                .setInterpolator(LinearInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }

        binding.dislikeLayer.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Slow.duration)
                .setInterpolator(LinearInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }

        binding.playLayer.setOnClickListener {
            val currentMovie = viewModel.getCurrentItem()
            if ( currentMovie != null){
                findNavController().navigate(
                    CompilationFragmentDirections.actionCompilationFragmentToMovieFragment(
                        currentMovie.getNavigationModel()
                    )
                )
            } else{
                val dialogFragment = ErrorDialogFragment(requireContext().getString(R.string.error_films_is_empty))
                requireActivity().let { it1 -> dialogFragment.show(it1.supportFragmentManager, getString(R.string.problems)) }
            }

        }

        return binding.root
    }

    private fun iniStackView(){
        cardStackView = binding.stackView

        manager = CardStackLayoutManager(requireContext(), this)
        manager.setStackFrom(StackFrom.Top)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(30.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(false)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCompilationLiveData().observe(viewLifecycleOwner){
            when (it) {
                ApiResponse.Loading -> {

                }
                is ApiResponse.Failure -> {
                    val errorDialog = ErrorDialogFragment(requireContext().getString(R.string.error_get_compilation))
                    errorDialog.show(requireActivity().supportFragmentManager, getString(R.string.problems))
                }
                is ApiResponse.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.emptyCompilationGroup.visibility = View.VISIBLE
                    adapter.notifyDataSetChanged()
                    binding.titleText.text = viewModel.getCurrentItemText()
                }
            }
        }
    }

    override fun onCardDragging(direction: Direction, ratio: Float) {
        Log.d("CardStackView", "onCardDragging: d = ${direction.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: Direction) {

        if (direction == Direction.Left){
            viewModel.onDislikeClick()
        } else{
            viewModel.onLikeClick()
        }

        viewModel.nextItem()
        binding.titleText.text = viewModel.getCurrentItemText()
    }

    override fun onCardRewound() {
        Log.d("CardStackView", "onCardRewound: ${manager.topPosition}")
    }

    override fun onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled: ${manager.topPosition}")
    }

    override fun onCardAppeared(view: View, position: Int) {

    }

    override fun onCardDisappeared(view: View, position: Int) {

    }
}