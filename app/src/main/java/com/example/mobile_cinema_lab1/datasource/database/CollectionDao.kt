package com.example.mobile_cinema_lab1.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface CollectionDao {

    @Insert
    fun insertCollection(collection: CollectionDBModel)

    @Query("SELECT * FROM collection where id = :collectionId")
    fun getCollection(collectionId: String): CollectionDBModel?

    @Query("UPDATE collection SET collection_name = :newName WHERE id = :collectionId")
    fun updateCollectionName(collectionId: String, newName: String)

    @Query("UPDATE collection SET icon_id = :newIcon WHERE id = :collectionId")
    fun updateCollectionIcon(collectionId: String, newIcon: String)

    @Query("DELETE from collection WHERE id = :collectionId")
    fun deleteCollection(collectionId: String)


}