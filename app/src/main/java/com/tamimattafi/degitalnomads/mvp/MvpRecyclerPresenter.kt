package com.tamimattafi.degitalnomads.mvp

import com.tamimattafi.degitalnomads.repository.global.MvpListRepository
import com.tamimattafi.degitalnomads.repository.global.MvpRepository

abstract class MvpRecyclerPresenter<OBJECT : MvpRecyclerContract.MvpObject, HOLDER : MvpRecyclerContract.MvpHolder>
    (protected val view: MvpRecyclerContract.MvpView<HOLDER>, protected val repository: MvpListRepository<OBJECT>) :
    MvpRecyclerContract.MvpPresenter<HOLDER> {

    protected val data: ArrayList<OBJECT> = ArrayList()
    abstract fun requestRepositoryData(paginationSize: Int)

    override fun loadMoreRecyclerData(recycler: MvpRecyclerContract.MvpRecycler) {
        with(recycler) {
            if (data.size <= listItemCount && !isLoading && !allData) {
                isLoading = true
                repository.apply {
                    setOnListSuccessListener(object : MvpListRepository.ListSuccessListener<OBJECT> {
                        override fun onSuccess(list: ArrayList<OBJECT>) {
                            data.addAll(list)
                            addMoreData(list.size)
                            allData = list.size < paginationSize
                            networkError = false
                            isLoading = false
                            setViewRefreshing(false)
                        }
                    })

                    setOnFailureListener(object : MvpRepository.FailureListener {
                        override fun onFailure(message: String?) {
                            addMoreData(0)
                            networkError = true
                            isLoading = false
                            view.showError(message.toString())
                            setViewRefreshing(false)
                        }
                    })

                    requestRepositoryData(recycler.paginationSize)
                }
            }
        }
    }

    open fun setViewRefreshing(refreshing: Boolean) {
        view.setRefreshing(refreshing)
    }

    override fun refresh(recycler: MvpRecyclerContract.MvpRecycler) {
        setViewRefreshing(true)
        if (data.isNotEmpty()) {
            repository.refresh()
            data.clear()
        }
        loadMoreRecyclerData(recycler)

    }

    override fun getItemSpecialValue(position: Int): String? {
        return if (data.size > position) {
            data[position].getItemSpecialValue()
        } else null
    }

    override fun onDestroyView() {
        repository.stopListening()
    }

    override fun onDestroy() {
        data.clear()
        repository.destroy()
    }

}