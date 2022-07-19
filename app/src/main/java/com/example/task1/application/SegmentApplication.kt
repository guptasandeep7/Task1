package com.example.task1.application

import android.app.Application
import com.example.task1.database.SegmentRoomDatabase
import com.example.task1.repo.SegmentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class SegmentApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { SegmentRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { SegmentRepository(database.segmentDao()) }
}