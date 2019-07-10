package com.tamimattafi.degitalnomads.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tamimattafi.degitalnomads.mvp.MvpRecyclerContract

@Entity(tableName = "news")
data class Article(
    var author: String? = null,
    var title: String? = null,
    var description: String? = null,
    @PrimaryKey var url: String,
    var urlToImage: String? = null,
    var publishedAt: String? = null,
    var content : String? = null
) : MvpRecyclerContract.MvpObject {
    override fun getItemSpecialValue(): String? = url
}
