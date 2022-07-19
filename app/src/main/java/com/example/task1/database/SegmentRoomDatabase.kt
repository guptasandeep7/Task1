package com.example.task1.database

import android.content.Context
import android.graphics.Color
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.task1.model.Segment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Segment::class), version = 1, exportSchema = false)
abstract class SegmentRoomDatabase : RoomDatabase() {


    abstract fun segmentDao(): SegmentDao

    private class SegmentDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.segmentDao())
                }
            }
        }

        suspend fun populateDatabase(segmentDao: SegmentDao) {
            // Delete all content here.
            segmentDao.deleteAll()

            val segmentList = mutableListOf<Segment>()
            segmentList.add(Segment(1, 32, Color.parseColor("#EF5350")))
            segmentList.add(Segment(33, 56, Color.parseColor("#FFA726")))
            segmentList.add(Segment(57, 85, Color.parseColor("#29B6F6")))
            segmentList.add(Segment(86, 100, Color.parseColor("#66BB6A")))

            segmentDao.insertAll(segmentList)


        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: SegmentRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): SegmentRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SegmentRoomDatabase::class.java,
                    "word_database"
                )
                    .addCallback(SegmentDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}