package com.mada.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mada.data.database.entity.TransHistoryEntity

@Dao
interface TransHistoryDao {
    @Query("SELECT * FROM transactions")
    fun getTransactions(): PagingSource<Int, TransHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TransHistoryEntity>)

    @Query("SELECT COUNT(id) FROM transactions")
    suspend fun count(): Long

    @Query("DELETE FROM transactions")
    suspend fun clearAll()
}