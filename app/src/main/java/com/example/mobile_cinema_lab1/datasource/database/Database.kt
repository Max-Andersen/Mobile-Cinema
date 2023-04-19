package com.example.mobile_cinema_lab1.datasource.database

import androidx.room.Room
import com.example.mobile_cinema_lab1.forapplication.MyApplication

object Database {
    private val database = Room.databaseBuilder(MyApplication.applicationContext(), CollectionDatabase::class.java, "collection").build()

    fun getDao() = database.collectionDao()
}