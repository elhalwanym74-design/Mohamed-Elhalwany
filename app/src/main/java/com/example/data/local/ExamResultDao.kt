package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamResultDao {
    @Query("SELECT * FROM exam_results ORDER BY timestamp DESC")
    fun getAllResults(): Flow<List<ExamResultEntity>>

    @Insert
    suspend fun insertResult(result: ExamResultEntity): Long

    @Query("SELECT COUNT(*) FROM exam_results")
    fun getTotalExamsCount(): Flow<Int>

    @Query("SELECT AVG(percentage) FROM exam_results")
    fun getAveragePercentage(): Flow<Float?>
}
