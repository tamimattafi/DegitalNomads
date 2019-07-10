package com.tamimattafi.degitalnomads.model.database

import androidx.room.*
import com.tamimattafi.degitalnomads.model.Article

@Dao
interface NewsDao : BaseDao<Article> {

    @Query("SELECT * FROM news ORDER BY publishedAt DESC LIMIT :pageSize OFFSET :currentTotal")
    fun getAllNews(pageSize : Int, currentTotal : Int) : List<Article>

    @Query("DELETE FROM news")
    fun deleteAll()
}