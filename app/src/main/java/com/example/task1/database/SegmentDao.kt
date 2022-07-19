package com.example.task1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.task1.model.Segment
import kotlinx.coroutines.flow.Flow

@Dao
interface SegmentDao {
    @Query("SELECT * FROM segment_table ORDER BY start ASC")
    fun getAllSegments(): Flow<List<Segment>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(segmentList: MutableList<Segment>)

    @Query("DELETE FROM segment_table")
    suspend fun deleteAll()
}