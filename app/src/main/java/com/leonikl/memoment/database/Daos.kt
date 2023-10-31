package com.leonikl.memoment.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPage(page: Page)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePage(page: Page)

    @Delete
    fun deletePage(page: Page)

    @Query("SELECT * FROM page")
    fun getAllPages(): LiveData<List<Page>>
}
