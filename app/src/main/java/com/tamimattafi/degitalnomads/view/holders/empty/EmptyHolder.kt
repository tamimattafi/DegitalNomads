package com.tamimattafi.degitalnomads.view.holders.empty

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tamimattafi.degitalnomads.mvp.MvpRecyclerContract
import com.tamimattafi.degitalnomads.utils.AppUtils
import kotlinx.android.synthetic.main.item_view_holder_bottom_error.view.*

class EmptyHolder(private val emptyHolderData : EmptyHolderData, itemView : View) : RecyclerView.ViewHolder(itemView), MvpRecyclerContract.MvpUnbindableHolder {

    init {
        with(itemView) {
            emptyHolderData.apply {
                emptyLabel.text = label
                emptyDescription.text = description
                emptyImage.setImageDrawable(AppUtils.getDrawable(context, drawable))
                emptyButton.text = actionName
            }
        }
    }

    override fun bindListener(listener: MvpRecyclerContract.MvpHolderListener) {
        itemView.emptyButton.setOnClickListener {
            listener.onHolderAction(
                -1,
                adapterPosition,
                null,
                emptyHolderData.action
            )
        }
    }

}