package com.example.mobile_cinema_lab1.repositories

import com.example.mobile_cinema_lab1.datasource.network.Network
import com.example.mobile_cinema_lab1.datasource.network.api.PreferencesApi
import com.example.mobile_cinema_lab1.datasource.network.api.TagsApi
import com.example.mobile_cinema_lab1.datasource.network.models.Tag
import com.example.mobile_cinema_lab1.domain.repositoryinterfaces.IPreferencesRepository

class PreferencesRepository: IPreferencesRepository, BaseRepository() {
    private val tagsApi: TagsApi = Network.getTagsApi()

    private val preferencesApi: PreferencesApi = Network.getPreferencesApi()

    override fun getTags() = apiRequestFlow { tagsApi.getTags() }

    override fun getUserPreferences() = apiRequestFlow { preferencesApi.getUserPreferences() }

    override fun editUserPreferences(newPreferences: List<Tag>) =
        apiRequestFlow { preferencesApi.updateUserPreferences(newPreferences) }

}