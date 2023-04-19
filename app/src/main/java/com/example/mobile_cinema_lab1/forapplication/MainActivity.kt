package com.example.mobile_cinema_lab1.forapplication

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_cinema_lab1.NavGraphXmlDirections
import com.example.mobile_cinema_lab1.R
import com.example.mobile_cinema_lab1.databinding.ActivityMainBinding
import com.example.mobile_cinema_lab1.domain.usecases.SharedPreferencesUseCase
import com.example.mobile_cinema_lab1.forapplication.errorhandling.ErrorDialogFragment
import com.example.mobile_cinema_lab1.forapplication.errorhandling.TroubleShooting

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var dialogFragment: ErrorDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.frameLayout) as NavHostFragment

        val navController: NavController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        binding.bottomNavigation.menu.forEach {
            val view = binding.bottomNavigation.findViewById<View>(it.itemId)
            view.setOnLongClickListener {
                true
            }
        }

        dialogFragment = ErrorDialogFragment(getString(R.string.error_update_token))


        if (SharedPreferencesUseCase().getAccessToken() != "") {
            navController.navigate(NavGraphXmlDirections.actionGlobalMainFragment())
        }

        TroubleShooting.getLiveDataForRefreshTrouble().observe(this) {
            if (it == true) {
                TroubleShooting.updateLiveDataForRefreshTrouble(false)
                dialogFragment.show(this.supportFragmentManager, "Problems")
                SharedPreferencesUseCase()
                    .clearUserData()
                navController.navigate(NavGraphXmlDirections.actionGlobalSignInFragment())
            }
        }
    }

    fun showBottomNavigation() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }

    fun hideBottomNavigation() {
        binding.bottomNavigation.visibility = View.INVISIBLE
    }

    class MarginItemDecoration(private val spaceSize: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect, view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            with(outRect) {
                top = spaceSize
                bottom = spaceSize
                left = spaceSize
                right = spaceSize
            }
        }
    }

    fun getMarginItemDecoration(dp: Int): MarginItemDecoration {
        return MarginItemDecoration(dp)
    }
}
