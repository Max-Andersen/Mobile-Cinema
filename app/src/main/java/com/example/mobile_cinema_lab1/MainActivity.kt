package com.example.mobile_cinema_lab1

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mobile_cinema_lab1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TroubleShooting.getLiveDataForRefreshTrouble().observe(this){
            if (it == true){ // TODO Routing to login screen and clearing all user data
                Toast.makeText(this, "Problem with refresh token", Toast.LENGTH_LONG).show()
                TroubleShooting.updateLiveDataForRefreshTrouble(false)
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        supportFragmentManager.beginTransaction().add(binding.frameLayout.id, MainFragment())
//            .commit()
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



//        binding.bottomNavigation.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.main -> Log.d("!", "MAIN")
//                R.id.compilation -> Log.d("!", "COMPILATION")
//                R.id.collections -> Log.d("!", "COLLECTION")
//                R.id.profile -> Log.d("!", "PROFILE")
//            }
//            true
//        }
    }
}
