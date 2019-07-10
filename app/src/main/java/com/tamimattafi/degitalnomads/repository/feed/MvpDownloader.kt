package com.tamimattafi.degitalnomads.repository.feed

import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MvpDownloader : MvpBackgroundInteraction<String, InputStream>() {

    override fun doInBackground(vararg p0: String): InputStream? {
        return try {
            if (isWorking) {
                downloadUrl(p0[0])
            } else null
        } catch (e : Exception) {
            null
        }
    }

    companion object {
        @Throws(IOException::class)
        private fun downloadUrl(urlString: String): InputStream? {
            val url = URL(urlString)
            return (url.openConnection() as? HttpURLConnection)?.run {
                readTimeout = 10000
                connectTimeout = 15000
                requestMethod = "GET"
                doInput = true
                connect()
                inputStream
            }
        }
    }
}