package com.tamimattafi.degitalnomads.repository.articles
import com.tamimattafi.degitalnomads.model.Article
import com.tamimattafi.degitalnomads.model.database.NewsDatabase
import com.tamimattafi.degitalnomads.repository.feed.FeedUtils
import com.tamimattafi.degitalnomads.repository.feed.MvpFeedContract
import com.tamimattafi.degitalnomads.repository.global.MvpListRepository

class MvpArticleRepository(private val preferences: MvpFeedContract.MvpDataPreferences) : MvpListRepository<Article>() {

    override val currentPage: Int
        get() = if (currentItemsSize > 0) (currentItemsSize / FeedUtils.SINGLE_PAGE_SIZE) + 1 else 1

    private val feedManager : MvpFeedContract.MvpFeedInteraction<String, Boolean> by lazy {
        MvpArticleFeedManager(
            NewsDatabase.DAO_INSTANCE
        )
    }

    private val headersManager : MvpFeedContract.MvpFeedInteraction<String, Int> by lazy {
        MvpArticleHeadersManager()
    }


    override fun getNextPage(paginationSize: Int) {
        if (currentItemsSize == 0) {
            headersManager.apply {
                addCompleteListener(object : MvpFeedContract.MvpCompleteListener<Int> {
                    override fun onComplete(result: Int) {
                        if (result > preferences.getDataSize()) {
                            preferences.setDataSize(result)
                            NewsDatabase.DAO_INSTANCE.deleteAll()
                        }
                        getPage(paginationSize)
                    }

                    override fun onFailure(message: String?) {
                        getPage(paginationSize)
                    }
                })
                start(FeedUtils.getPageLink(1))
            }
        }
        else getPage(paginationSize)
    }

    private fun getPage(paginationSize: Int) {
        if (currentPage <= FeedUtils.MAX_PAGE_COUNT) {
            NewsDatabase.DAO_INSTANCE.getAllNews(paginationSize, currentItemsSize).apply {
                if (isNotEmpty()) {
                    currentItemsSize += size
                    listSuccessListener?.onSuccess(ArrayList(this))
                    attempts = 0
                } else {
                    feedManager.apply {
                        addCompleteListener(object : MvpFeedContract.MvpCompleteListener<Boolean> {
                            override fun onComplete(result: Boolean) {
                                if (result) getNextPage(paginationSize)
                                else failureListener?.onFailure(FeedUtils.FEED_ERROR)
                            }
                            override fun onFailure(message: String?) {
                                failureListener?.onFailure(message)
                            }
                        })

                        start(FeedUtils.getPageLink(currentPage))
                    }
                }
            }
        } else listSuccessListener?.onSuccess(ArrayList())
    }



    override fun stopListening() {
        super.stopListening()
        headersManager.stop()
        feedManager.stop()
    }
}