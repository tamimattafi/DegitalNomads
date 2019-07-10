package com.tamimattafi.degitalnomads.mvp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tamimattafi.degitalnomads.R
import com.tamimattafi.degitalnomads.view.holders.empty.EmptyHolder
import com.tamimattafi.degitalnomads.utils.PhoneUtils
import com.tamimattafi.degitalnomads.view.holders.empty.EmptyHolderList
import com.tamimattafi.degitalnomads.view.holders.empty.UnbindableHolder

abstract class MvpRecyclerAdapter<HOLDER : MvpRecyclerContract.MvpHolder>(
    private val presenter: MvpRecyclerContract.MvpPresenter<HOLDER>,
    private val listener: MvpRecyclerContract.MvpHolderListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    MvpRecyclerContract.MvpRecycler {

    override var listItemCount: Int = 0
    override var headersCount: Int = 0
    override var footersCount: Int = 0
    override var allData: Boolean = false
    override var isLoading: Boolean = false
    override var networkError: Boolean = false

    private val ITEM_HEADER = Int.MAX_VALUE - 1
    private val ITEM_NO_DATA = ITEM_HEADER - 1
    private val ITEM_LOADING = ITEM_NO_DATA - 1
    private val ITEM_LOADING_ERROR = ITEM_LOADING - 1
    private val ITEM_MAIN = ITEM_LOADING_ERROR - 1
    private val ITEM_FOOTER = ITEM_MAIN - 1

    override fun addMoreData(newDataCount: Int) {
        listItemCount += newDataCount
        notifyDataSetChanged()
    }

    override fun deleteItem(position: Int) {
        deleteItem(position + headersCount)
    }

    override fun refresh() {
        listItemCount = 0
        networkError = false
        allData = false
        isLoading = false
        presenter.refresh(this)
    }

    override fun getItemCount(): Int {
        return if (listItemCount > 0) {
            listItemCount + footersCount + headersCount + if (allData) 0 else 1
        } else 1
    }

    override fun loadMoreData() {
        presenter.loadMoreRecyclerData(this)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MvpRecyclerContract.MvpUnbindableHolder) {
            holder.bindListener(listener)
        }
        else (holder as? HOLDER)?.apply {
            this.listPosition = position - headersCount
            bindListener(listener)
            presenter.bindRecyclerHolder(this)
        }
    }

    abstract fun getItemHolder(parent: ViewGroup): RecyclerView.ViewHolder
    abstract fun getNoDataHolderType(): Int

    open fun getNoDataHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
        return EmptyHolder(
            EmptyHolderList.getItem(parent.context, type),
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view_holder_empty,
                parent,
                false
            )
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        with(LayoutInflater.from(parent.context)) {
            return when {
                viewType == ITEM_MAIN -> getItemHolder(parent)
                viewType == ITEM_NO_DATA && (!PhoneUtils.isConnected(parent.context) || networkError) -> getNoDataHolder(
                    parent,
                    EmptyHolderList.NO_CONNECTION
                )
                viewType == ITEM_NO_DATA -> getNoDataHolder(parent, getNoDataHolderType())
                viewType == ITEM_LOADING_ERROR -> EmptyHolder(
                    EmptyHolderList.getItem(parent.context, EmptyHolderList.TRY_AGAIN),
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_view_holder_bottom_error,
                        parent,
                        false
                    )
                )
                else -> UnbindableHolder(
                    inflate(
                        R.layout.item_view_holder_bottom_loading,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun tryAgain() {
        allData = false
        isLoading = false
        networkError = false
        notifyDataSetChanged()
        loadMoreData()
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            listItemCount > 0 && headersCount > 0 && position in 0 until headersCount -> ITEM_HEADER
            listItemCount > 0 && position in headersCount until listItemCount + headersCount -> ITEM_MAIN
            listItemCount > 0 && footersCount > 0 && position in listItemCount + headersCount until listItemCount + headersCount + footersCount -> ITEM_FOOTER
            listItemCount > 0 && position == listItemCount + headersCount + footersCount && networkError -> ITEM_LOADING_ERROR
            listItemCount == 0 -> ITEM_NO_DATA
            else -> ITEM_LOADING
        }
    }

    fun startListening() {
        allData = false
        presenter.loadMoreRecyclerData(this)
    }

}