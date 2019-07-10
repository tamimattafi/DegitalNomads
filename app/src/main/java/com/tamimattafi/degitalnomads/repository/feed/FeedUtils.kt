package com.tamimattafi.degitalnomads.repository.feed

object FeedUtils {

    private const val MAIN_LINK = "https://newsapi.org/v2/everything?q=android&from=2019-04-00&sortBy=publishedAt&apiKey=26eddb253e7840f988aec61f2ece2907&page=[PAGE_NUMBER]"
    const val SINGLE_PAGE_SIZE = 20
    const val MAX_PAGE_COUNT = 5

    const val FEED_ERROR = "Something went wrong, please try again"

    fun getPageLink(pageNumber : Int) : String = MAIN_LINK.replace("[PAGE_NUMBER]", pageNumber.toString())
}