package com.example.mobilecinemalab.repositories

import com.example.mobilecinemalab.datasource.network.Network
import com.example.mobilecinemalab.datasource.network.api.PreferencesApi
import com.example.mobilecinemalab.datasource.network.api.TagsApi
import com.example.mobilecinemalab.datasource.network.models.Tag
import com.example.mobilecinemalab.domain.repositoryinterfaces.IPreferencesRepository

class PreferencesRepository: IPreferencesRepository, BaseRepository() {
    private val tagsApi: TagsApi = Network.getTagsApi()

    private val preferencesApi: PreferencesApi = Network.getPreferencesApi()

    override fun getTags() = apiRequestFlow { tagsApi.getTags() }

    override fun getUserPreferences() = apiRequestFlow { preferencesApi.getUserPreferences() }

    override fun editUserPreferences(newPreferences: List<Tag>) =
        apiRequestFlow { preferencesApi.updateUserPreferences(newPreferences) }

}