package com.example.bestlibrary.librariesclass.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LibraryDao {

    @Query(
        """
        SELECT *
          FROM library_items
         ORDER BY 
           CASE WHEN :sortBy = 'name' THEN name END,
           CASE WHEN :sortBy = 'date' THEN timestamp END
         LIMIT :limit OFFSET :offset
    """
    )
    suspend fun loadPage(sortBy: String, limit: Int, offset: Int): List<LibraryEntity>

    @Insert
    suspend fun insert(item: LibraryEntity)

    @Query("SELECT COUNT(*) FROM library_items")
    suspend fun count(): Int

    @Delete
    suspend fun delete(item: LibraryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<LibraryEntity>)
}
