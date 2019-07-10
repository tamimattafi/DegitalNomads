package com.tamimattafi.degitalnomads.view.holders.news

import android.view.View
import com.squareup.picasso.Picasso
import com.tamimattafi.degitalnomads.R
import com.tamimattafi.degitalnomads.mvp.MvpRecyclerContract
import com.tamimattafi.degitalnomads.mvp.MvpRecyclerHolder
import com.tamimattafi.degitalnomads.presentation.home.MvpHomeContract
import com.tamimattafi.degitalnomads.utils.AppUtils
import com.tamimattafi.degitalnomads.utils.DateUtils
import kotlinx.android.synthetic.main.item_view_holder_news.view.*

class MvpNewsHolder(itemView : View) : MvpRecyclerHolder(itemView),
    MvpHomeContract.MvpNewsHolder {

    override fun bindListener(listener: MvpRecyclerContract.MvpHolderListener) {
        with(itemView) {
            setOnClickListener {
                listener.onHolderClick(listPosition, adapterPosition)
            }
            setOnLongClickListener {
                listener.onHolderLongClick(listPosition, adapterPosition)
                true
            }
        }
    }

    override fun setAuthor(author: String?) {
        author?.apply {
            with(itemView.author) {
                text = this@apply
                visibility = View.VISIBLE
            }
        }
    }

    override fun setTitle(title: String?) {
        itemView.title.text = title
    }

    override fun setDescription(description: String?) {
        itemView.description.text = description
    }

    override fun setImage(urlToImage: String?) {
        if (!urlToImage.isNullOrEmpty()) {
            Picasso.get().load(urlToImage)
                .error(R.drawable.img_error)
                .placeholder(R.drawable.img_placeholder)
                .into(itemView.image)
        } else itemView.image.setImageDrawable(AppUtils.getDrawable(itemView.context, R.drawable.img_error))
    }

    override fun setPublishDate(date: String?) {
        itemView.date.text = DateUtils.changePattern(date ?: return, DateUtils.SERVER_DATE_PATTERN, DateUtils.UI_DATE_PATTERN)
    }
}