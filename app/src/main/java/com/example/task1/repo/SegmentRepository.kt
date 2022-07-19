package com.example.task1.repo

import androidx.annotation.WorkerThread
import com.example.task1.model.Segment
import com.example.task1.database.SegmentDao
import kotlinx.coroutines.flow.Flow

class SegmentRepository(private val segmentDao: SegmentDao) {

    val allSegments: Flow<List<Segment>> = segmentDao.getAllSegments()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAll(segmentList: MutableList<Segment>) {
        segmentDao.insertAll(segmentList)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        segmentDao.deleteAll()
    }
}