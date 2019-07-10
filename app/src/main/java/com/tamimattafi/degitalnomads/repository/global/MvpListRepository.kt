package com.tamimattafi.degitalnomads.repository.global

abstract class MvpListRepository<OBJECT> : MvpRepository<OBJECT>() {

    abstract val currentPage : Int
    protected var currentItemsSize : Int = 0
    protected var attempts = 0

    protected var listSuccessListener: ListSuccessListener<OBJECT>? = null

    override fun stopListening() {
        super.stopListening()
        listSuccessListener = null
    }

    abstract fun getNextPage(paginationSize: Int)

    fun setOnListSuccessListener(listSuccessListener: ListSuccessListener<OBJECT>) {
        this.listSuccessListener = listSuccessListener
    }


    interface ListSuccessListener<OBJECT> {
        fun onSuccess(list: ArrayList<OBJECT>)
    }

    override fun refresh() {
        currentItemsSize = 0
    }

}