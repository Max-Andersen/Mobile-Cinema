package com.example.mobilecinemalab.datasource.network.models


@kotlinx.serialization.Serializable
data class Tag (
	val tagId : String,
	val tagName : String,
	val categoryName : String
)