package com.leonikl.memoment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Page::class)], version = 1)
abstract class PageRoomDatabase: RoomDatabase() {
    abstract fun pageDao(): PageDao

    companion object {

        private var INSTANCE: PageRoomDatabase? = null

        fun getInstance(context: Context): PageRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PageRoomDatabase::class.java,
                        "page_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}