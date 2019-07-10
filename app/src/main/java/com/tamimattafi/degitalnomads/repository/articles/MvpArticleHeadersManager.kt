package com.tamimattafi.degitalnomads.repository.articles
import com.tamimattafi.degitalnomads.repository.feed.MvpDownloader
import com.tamimattafi.degitalnomads.repository.feed.MvpFeedContract
import java.io.InputStream

class MvpArticleHeadersManager :
    MvpFeedContract.MvpFeedInteraction<String, Int> {

    private var isWorking = true
    private var completeListener: MvpFeedContract.MvpCompleteListener<Int>? = null

    private var downloader : MvpDownloader? = null
    private var parser : MvpArticleHeadersParser? = null

    override fun start(source: String) {
        downloadFeed(source)
    }

    private fun downloadFeed(link : String) {
        MvpDownloader().apply {
            addCompleteListener(object :
                MvpFeedContract.MvpCompleteListener<InputStream> {
                override fun onComplete(result: InputStream) {
                    parseFeed(result)
                }

                override fun onFailure(message: String?) {
                    completeListener?.onFailure(message)
                }

            })
            execute(link)

            downloader = this
        }
    }

    private fun parseFeed(result: InputStream) {
        MvpArticleHeadersParser().apply {
            addCompleteListener(object :
                MvpFeedContract.MvpCompleteListener<Int> {
                override fun onComplete(result: Int) {
                    completeListener?.onComplete(result)
                }

                override fun onFailure(message: String?) {
                    completeListener?.onFailure(message)
                }

            })
            execute(result)

            parser = this
        }
    }

    override fun stop() {
        isWorking = false
        downloader?.stop()
        parser?.stop()
    }

    override fun addCompleteListener(completeListener: MvpFeedContract.MvpCompleteListener<Int>?) {
        this.completeListener = completeListener
    }


}