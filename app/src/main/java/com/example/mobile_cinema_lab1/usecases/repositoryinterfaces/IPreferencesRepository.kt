package com.example.mobile_cinema_lab1.usecases.repositoryinterfaces

import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Tag
import kotlinx.coroutines.flow.Flow

interface IPreferencesRepository {

    fun getTags(): Flow<ApiResponse<List<Tag>>>

    fun getUserPreferences(): Flow<ApiResponse<List<Tag>>>

    fun editUserPreferences(newPreferences: List<Tag>): Flow<ApiResponse<Nothing>>
}