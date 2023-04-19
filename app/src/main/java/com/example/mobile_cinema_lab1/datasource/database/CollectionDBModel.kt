package com.example.mobile_cinema_lab1.datasource.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collection")
data class CollectionDBModel(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "collection_name") val name: String,
    @ColumnInfo(name = "icon_id") val iconId: String
)