package com.tamimattafi.degitalnomads.view.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tamimattafi.degitalnomads.R
import com.tamimattafi.degitalnomads.mvp.MvpRecyclerAdapter
import com.tamimattafi.degitalnomads.mvp.MvpRecyclerContract
import com.tamimattafi.degitalnomads.presentation.home.MvpHomeContract
import com.tamimattafi.degitalnomads.view.holders.empty.EmptyHolderList
import com.tamimattafi.degitalnomads.view.holders.news.MvpNewsHolder

class MvpHomeAdapter (presenter : MvpRecyclerContract.MvpPresenter<MvpHomeContract.MvpNewsHolder>, listener : MvpRecyclerContract.MvpHolderListener) : MvpRecyclerAdapter<MvpHomeContract.MvpNewsHolder>(presenter, listener) {

    override var paginationSize: Int = 5

    override fun getItemHolder(parent: ViewGroup): RecyclerView.ViewHolder
                = MvpNewsHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_view_holder_news,
                        parent,
                        false
                    )
                )

    override fun getNoDataHolderType(): Int = EmptyHolderList.EMPTY_LIST

}