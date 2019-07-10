package com.tamimattafi.degitalnomads.repository.feed

import android.os.AsyncTask


abstract class MvpBackgroundInteraction<SOURCE, RESULT> : AsyncTask<SOURCE, Int, RESULT?>(), MvpFeedContract.MvpFeedInteraction<SOURCE, RESULT> {

    protected var isWorking: Boolean = true
    private var completeListener : MvpFeedContract.MvpCompleteListener<RESULT>? = null

    override fun start(source: SOURCE) {
        super.execute(source)
    }

    override fun stop() {
        isWorking = false
    }


    override fun addCompleteListener(completeListener: MvpFeedContract.MvpCompleteListener<RESULT>?) {
        this.completeListener = completeListener
    }

    override fun onPostExecute(result: RESULT?) {
        completeListener?.apply {
            if (result != null) onComplete(result) else onFailure(FeedUtils.FEED_ERROR)
        }
        super.onPostExecute(result)
    }

}