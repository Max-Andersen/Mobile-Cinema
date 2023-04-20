package com.example.mobilecinemalab.datasource.database

class DatabaseRepository: IDatabaseRepository {
    private val databaseDao = Database.getDao()

    override fun getCollection(collectionId: String) = databaseDao.getCollection(collectionId)

    override fun insertCollection(collection: CollectionDBModel) = databaseDao.insertCollection(collection)

    override fun deleteCollection(collectionId: String) = databaseDao.deleteCollection(collectionId)

    override fun updateCollectionIcon(collectionId: String, iconId: String) = databaseDao.updateCollectionIcon(collectionId, iconId)

    override fun updateCollectionName(collectionId: String, newName: String) = databaseDao.updateCollectionName(collectionId, newName)
}