package com.example.bestlibrary.librariesclass.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LibraryRepository(context: Context) {
    private val dao = AppDatabase.getInstance(context).libraryDao()

    suspend fun getPage(sortBy: String, limit: Int, offset: Int): List<LibraryEntity> =
        withContext(Dispatchers.IO) {
            dao.loadPage(sortBy, limit, offset)
        }

    suspend fun add(item: LibraryEntity) =
        withContext(Dispatchers.IO) {
            dao.insert(item)
        }

    suspend fun delete(item: LibraryEntity) =
        withContext(Dispatchers.IO) {
            dao.delete(item)
        }
}
