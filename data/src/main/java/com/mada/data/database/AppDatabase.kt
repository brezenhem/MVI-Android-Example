package com.mada.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mada.data.database.dao.TransHistoryDao
import com.mada.data.database.entity.TransHistoryEntity

@Database(entities = [TransHistoryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transDao(): TransHistoryDao
}