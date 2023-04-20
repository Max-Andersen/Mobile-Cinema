package com.example.mobilecinemalab.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [CollectionDBModel::class], version = 1)
abstract class CollectionDatabase: RoomDatabase() {

    abstract fun collectionDao(): CollectionDao

}