package com.example.mobilecinemalab.datasource.database

import androidx.room.Room
import com.example.mobilecinemalab.forapplication.MyApplication

object Database {
    private val database = Room.databaseBuilder(MyApplication.applicationContext(), CollectionDatabase::class.java, "collection").build()

    fun getDao() = database.collectionDao()
}