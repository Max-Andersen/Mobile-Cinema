package com.example.mobile_cinema_lab1.network.models


@kotlinx.serialization.Serializable
data class Tags (
	val tagId : String,
	val tagName : String,
	val categoryName : String
)