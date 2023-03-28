package com.example.mobile_cinema_lab1.usecases.repositoryinterfaces

import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Tags
import kotlinx.coroutines.flow.Flow

interface IPreferencesRepository {

    fun getTags(): Flow<ApiResponse<List<Tags>>>

    fun getUserPreferences(): Flow<ApiResponse<List<Tags>>>

    fun editUserPreferences(newPreferences: List<Tags>): Flow<ApiResponse<Nothing>>
}