package com.example.mobilecinemalab.domain.repositoryinterfaces

import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.Tag
import kotlinx.coroutines.flow.Flow

interface IPreferencesRepository {

    fun getTags(): Flow<ApiResponse<List<Tag>>>

    fun getUserPreferences(): Flow<ApiResponse<List<Tag>>>

    fun editUserPreferences(newPreferences: List<Tag>): Flow<ApiResponse<Nothing>>
}