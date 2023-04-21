package com.example.mobilecinemalab.forapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mobilecinemalab.NavGraphXmlDirections
import com.example.mobilecinemalab.R
import com.example.mobilecinemalab.databinding.ActivityMainBinding
import com.example.mobilecinemalab.domain.usecases.SharedPreferencesUseCase
import com.example.mobilecinemalab.forapplication.errorhandling.ErrorDialogFragment
import com.example.mobilecinemalab.forapplication.errorhandling.TroubleShooting

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var dialogFragment: ErrorDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {

        //setTheme(R.style.splashScreenTheme);
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


        if (SharedPreferencesUseCase().getIsFirstLaunch() == ""){
            navController.navigate(NavGraphXmlDirections.actionGlobalSignUpFragment())
            SharedPreferencesUseCase().updateIsFirstLaunch("1")
        } else{
            if (SharedPreferencesUseCase().getAccessToken() != "") {
                navController.navigate(NavGraphXmlDirections.actionGlobalMainFragment())
            }
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
}
