package com.tamimattafi.degitalnomads.repository.articles

import com.tamimattafi.degitalnomads.model.Article
import com.tamimattafi.degitalnomads.model.database.BaseDao
import com.tamimattafi.degitalnomads.repository.feed.MvpCashier
import com.tamimattafi.degitalnomads.repository.feed.MvpDownloader
import com.tamimattafi.degitalnomads.repository.feed.MvpFeedContract
import java.io.InputStream

class MvpArticleFeedManager(private val baseDao: BaseDao<Article>) :
    MvpFeedContract.MvpFeedInteraction<String, Boolean> {

    private var isWorking = true
    private var completeListener: MvpFeedContract.MvpCompleteListener<Boolean>? = null

    private var downloader : MvpDownloader? = null
    private var parser : MvpArticleParser? = null
    private var cashier : MvpCashier<Article>? = null

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
        MvpArticleParser().apply {
            addCompleteListener(object :
                MvpFeedContract.MvpCompleteListener<List<Article>> {
                override fun onComplete(result: List<Article>) {
                    cashFeed(result)
                }

                override fun onFailure(message: String?) {
                    completeListener?.onFailure(message)
                }

            })
            execute(result)

            parser = this
        }
    }

    private fun cashFeed(result: List<Article>) {
        MvpCashier(baseDao).apply {
            addCompleteListener(object :
                MvpFeedContract.MvpCompleteListener<Boolean> {
                override fun onComplete(result: Boolean) {
                    completeListener?.onComplete(result)
                }

                override fun onFailure(message: String?) {
                    completeListener?.onFailure(message)
                }

            })
            execute(result)

            cashier = this
        }
    }


    override fun stop() {
        isWorking = false
        downloader?.stop()
        parser?.stop()
        cashier?.stop()
    }

    override fun addCompleteListener(completeListener: MvpFeedContract.MvpCompleteListener<Boolean>?) {
        this.completeListener = completeListener
    }


}