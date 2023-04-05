package com.example.mobile_cinema_lab1.network.repositories

import com.example.mobile_cinema_lab1.network.Network
import com.example.mobile_cinema_lab1.network.api.PreferencesApi
import com.example.mobile_cinema_lab1.network.api.TagsApi
import com.example.mobile_cinema_lab1.network.models.Tag
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.IPreferencesRepository

class PreferencesRepository: IPreferencesRepository, BaseRepository() {
    private val tagsApi: TagsApi = Network.getTagsApi()

    private val preferencesApi: PreferencesApi = Network.getPreferencesApi()

    override fun getTags() = apiRequestFlow { tagsApi.getTags() }

    override fun getUserPreferences() = apiRequestFlow { preferencesApi.getUserPreferences() }

    override fun editUserPreferences(newPreferences: List<Tag>) =
        apiRequestFlow { preferencesApi.updateUserPreferences(newPreferences) }

}