package com.app.focustrack.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "screen_unlocks")
data class ScreenUnlock(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long,
    val date: String // Format: yyyy-MM-dd
)
