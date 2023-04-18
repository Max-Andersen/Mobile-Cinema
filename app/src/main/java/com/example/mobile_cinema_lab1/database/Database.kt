package com.example.mobile_cinema_lab1.database

import androidx.room.Room
import com.example.mobile_cinema_lab1.MyApplication

object Database {
    private val database = Room.databaseBuilder(MyApplication.applicationContext(), CollectionDatabase::class.java, "collection").build()

    fun getDao() = database.collectionDao()
}