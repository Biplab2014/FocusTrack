package com.app.focustrack.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.app.focustrack.data.dao.*
import com.app.focustrack.data.entity.*

@Database(
    entities = [
        AppUsage::class,
        FocusSession::class,
        ScreenUnlock::class,
        AppLimit::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FocusTrackDatabase : RoomDatabase() {
    
    abstract fun appUsageDao(): AppUsageDao
    abstract fun focusSessionDao(): FocusSessionDao
    abstract fun screenUnlockDao(): ScreenUnlockDao
    abstract fun appLimitDao(): AppLimitDao
    
    companion object {
        @Volatile
        private var INSTANCE: FocusTrackDatabase? = null
        
        fun getDatabase(context: Context): FocusTrackDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FocusTrackDatabase::class.java,
                    "focus_track_database"
                )
                .setJournalMode(RoomDatabase.JournalMode.TRUNCATE) // Better for resource management
                .build()
                INSTANCE = instance
                instance
            }
        }

        fun closeDatabase() {
            synchronized(this) {
                INSTANCE?.close()
                INSTANCE = null
            }
        }
    }
}
