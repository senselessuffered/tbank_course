package com.example.bestlibrary.librariesclass.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "library_items")
data class LibraryEntity(
    @PrimaryKey val id: Int,
    val type: String,
    val name: String,
    val extra: String,
    val timestamp: Long,
    val isAvailable: Boolean
) : Serializable
