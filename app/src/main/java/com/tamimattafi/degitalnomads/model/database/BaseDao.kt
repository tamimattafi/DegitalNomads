package com.tamimattafi.degitalnomads.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface BaseDao<OBJECT> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNews(newsList: List<OBJECT>)
}