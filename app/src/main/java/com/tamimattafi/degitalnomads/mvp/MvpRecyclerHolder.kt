package com.tamimattafi.degitalnomads.mvp

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class MvpRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    MvpRecyclerContract.MvpHolder {
    override var listPosition: Int = 0

}