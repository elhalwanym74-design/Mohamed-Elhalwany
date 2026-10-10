package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey
    val questionId: String,
    val subjectId: String,
    val savedAtTimestamp: Long = System.currentTimeMillis()
)
