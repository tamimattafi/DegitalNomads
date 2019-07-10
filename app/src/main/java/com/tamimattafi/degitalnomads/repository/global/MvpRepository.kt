package com.tamimattafi.degitalnomads.repository.global

import com.tamimattafi.degitalnomads.mvp.MvpRecyclerContract


abstract class MvpRepository<OBJECT> : MvpRecyclerContract.MvpRepository {
    protected var failureListener : FailureListener? = null

    override fun refresh() {
        stopListening()
    }

    open fun stopListening() {
        failureListener = null
    }

    override fun destroy() {
        stopListening()
    }



    fun setOnFailureListener(failureListener: FailureListener) {
        this.failureListener = failureListener
    }


    interface FailureListener {
        fun onFailure(message : String?)
    }

}