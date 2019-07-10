package com.tamimattafi.degitalnomads.repository.articles
import java.io.InputStream
import java.lang.Exception
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import com.google.gson.JsonParser
import com.tamimattafi.degitalnomads.model.Article
import com.tamimattafi.degitalnomads.repository.feed.MvpBackgroundInteraction


class MvpArticleParser : MvpBackgroundInteraction<InputStream, List<Article>>() {

    override fun doInBackground(vararg p0: InputStream): List<Article>? {
        val inputStream = p0[0]
        return try {
            if (isWorking) {
                val list = ArrayList<Article>()
                val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"), 8)
                val array = JsonParser().parse(reader).asJsonObject.getAsJsonArray("articles")
                for (jObject in array) {
                    if (isWorking) {
                        jObject.asJsonObject.apply {
                            remove("source")
                            list.add(Gson().fromJson(this, Article::class.java))
                        }
                    } else break
                }
                if (isWorking) list
                else null
            } else null
        } catch (e: Exception) {
            null
        } finally {
            try {
                inputStream.close()
            }
            catch (e : Exception) { }
        }
    }

}