package com.example.mobile_cinema_lab1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.TooltipCompat
import androidx.core.view.forEach
import androidx.core.view.iterator
import com.example.mobile_cinema_lab1.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(binding.frameLayout.id, MainFragment())
            .commit()

        binding.bottomNavigation.menu.forEach {
            val view = binding.bottomNavigation.findViewById<View>(it.itemId)
            view.setOnLongClickListener {
                true
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.main -> Log.d("!", "MAIN")
                R.id.compilation -> Log.d("!", "COMPILATION")
                R.id.collections -> Log.d("!", "COLLECTION")
                R.id.profile -> Log.d("!", "PROFILE")
            }
            true
        }
    }
}
