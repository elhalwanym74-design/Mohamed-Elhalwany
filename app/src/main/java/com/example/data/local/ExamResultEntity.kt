package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exam_results")
data class ExamResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subjectTitle: String,
    val score: Int,
    val totalQuestions: Int,
    val percentage: Int,
    val timeSpentSeconds: Int,
    val timestamp: Long = System.currentTimeMillis()
)
