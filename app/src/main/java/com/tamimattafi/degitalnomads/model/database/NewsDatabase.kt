package com.tamimattafi.degitalnomads.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.tamimattafi.degitalnomads.model.Article
import androidx.room.Room



@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getNewsDao() : NewsDao

    companion object {

        @Volatile
        lateinit var DAO_INSTANCE : NewsDao

        fun initDatabase(context: Context) : NewsDao {
            return if (::DAO_INSTANCE.isInitialized) DAO_INSTANCE else synchronized(this) {
                if (::DAO_INSTANCE.isInitialized) DAO_INSTANCE
                else Room.databaseBuilder(context, NewsDatabase::class.java, DatabaseValues.NEWS_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build().getNewsDao().also {
                        DAO_INSTANCE = it
                    }
            }
        }
    }
}