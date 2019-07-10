package com.tamimattafi.degitalnomads.mvp
interface MvpRecyclerContract {

    interface MvpRecycler {
        var paginationSize: Int
        var listItemCount: Int
        var headersCount: Int
        var footersCount: Int
        var allData: Boolean
        var isLoading: Boolean
        var networkError : Boolean
        fun addMoreData(newDataCount: Int)
        fun deleteItem(position: Int)
        fun refresh()
        fun loadMoreData()
        fun tryAgain()
    }

    interface MvpRepository {
        fun refresh()
        fun destroy()
    }

    interface MvpPresenter<HOLDER : MvpHolder> {
        fun loadMoreRecyclerData(recycler: MvpRecycler)
        fun bindRecyclerHolder(holder: HOLDER)
        fun refresh(recycler: MvpRecycler)
        fun getItemSpecialValue(position: Int): String?
        fun onDestroyView()
        fun onDestroy()
    }

    interface MvpHolder {
        var listPosition: Int
        fun bindListener(listener: MvpHolderListener)
    }

    interface MvpUnbindableHolder {
        fun bindListener(listener: MvpHolderListener)
    }

    interface MvpView<HOLDER : MvpHolder> :
        MvpHolderListener {
        fun setRefreshing(refreshing: Boolean)
        fun showError(message: String)
    }

    interface MvpHolderListener {
        fun onHolderClick(listPosition: Int, adapterPosition: Int, itemId : String? = null)
        fun onHolderLongClick(listPosition: Int, adapterPosition: Int, itemId : String? = null)
        fun onHolderAction(listPosition: Int, adapterPosition: Int, itemId : String? = null, actionType: Int)
    }

    interface MvpFilterListener {
        fun onRequestChangeFilter()
    }

    interface MvpObject {
        fun getItemSpecialValue(): String?
    }
}
