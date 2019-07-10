package com.tamimattafi.degitalnomads.repository.articles

import java.io.InputStream
import java.lang.Exception
import java.io.BufferedReader
import java.io.InputStreamReader
import com.google.gson.JsonParser
import com.tamimattafi.degitalnomads.repository.feed.MvpBackgroundInteraction


class MvpArticleHeadersParser : MvpBackgroundInteraction<InputStream, Int>() {

    override fun doInBackground(vararg p0: InputStream): Int? {
        val inputStream = p0[0]
        return try {
            if (isWorking) {
                val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"), 8)
                val count = JsonParser().parse(reader).asJsonObject.getAsJsonPrimitive("totalResults")
                if (count.isNumber) {
                    count.asNumber.toInt()
                } else null
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