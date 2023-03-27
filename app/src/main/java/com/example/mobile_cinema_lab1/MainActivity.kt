package com.example.mobile_cinema_lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        TroubleShooting.getLiveDataForRefreshTrouble().observe(this){
            if (it == true){ // TODO Routing to login screen and clearing all user data
                Toast.makeText(this, "Problem with refresh token", Toast.LENGTH_LONG).show()
                TroubleShooting.updateLiveDataForRefreshTrouble(false)
            }
        }


    }
}