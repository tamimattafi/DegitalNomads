package com.tamimattafi.degitalnomads.repository.feed

interface MvpFeedContract {

    interface MvpFeedInteraction<SOURCE, RESULT> {
        fun start(source : SOURCE)
        fun stop()
        fun addCompleteListener(completeListener: MvpCompleteListener<RESULT>?)
    }

    interface MvpCompleteListener<RESULT> {
        fun onComplete(result : RESULT)
        fun onFailure(message : String?)
    }

    interface MvpDataPreferences {
        fun getDataSize() : Int
        fun setDataSize(size: Int)
    }
}