package com.example.mobile_cinema_lab1.domain.repositoryinterfaces

import com.example.mobile_cinema_lab1.datasource.network.ApiResponse
import com.example.mobile_cinema_lab1.datasource.network.models.Tag
import kotlinx.coroutines.flow.Flow

interface IPreferencesRepository {

    fun getTags(): Flow<ApiResponse<List<Tag>>>

    fun getUserPreferences(): Flow<ApiResponse<List<Tag>>>

    fun editUserPreferences(newPreferences: List<Tag>): Flow<ApiResponse<Nothing>>
}