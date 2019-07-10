package com.tamimattafi.degitalnomads.view.fragments.home

import android.os.Bundle
import android.view.View
import com.tamimattafi.degitalnomads.R
import com.tamimattafi.degitalnomads.model.database.DatabaseValues
import com.tamimattafi.degitalnomads.mvp.MvpRecyclerAdapter
import com.tamimattafi.degitalnomads.mvp.MvpRecyclerContract
import com.tamimattafi.degitalnomads.mvp.MvpRecyclerFragment
import com.tamimattafi.degitalnomads.presentation.home.MvpHomeContract
import com.tamimattafi.degitalnomads.presentation.home.MvpHomePresenter
import com.tamimattafi.degitalnomads.repository.articles.MvpArticleRepository
import com.tamimattafi.degitalnomads.repository.feed.DataPreferences
import com.tamimattafi.degitalnomads.utils.AppUtils
import com.tamimattafi.degitalnomads.view.fragments.web.MvpWebFragment
import com.tamimattafi.degitalnomads.view.holders.empty.EmptyHolderList
import kotlinx.android.synthetic.main.toolbar_web.*


class MvpHomeFragment : MvpRecyclerFragment<MvpHomeContract.MvpNewsHolder>(), MvpHomeContract.MvpHomeView {


    override fun onSetUpPresenter(): MvpRecyclerContract.MvpPresenter<MvpHomeContract.MvpNewsHolder>
            = MvpHomePresenter(this, MvpArticleRepository(DataPreferences(context!!, DatabaseValues.KEY_FEED_SIZE)))

    override fun onSetUpAdapter(): MvpRecyclerAdapter<MvpHomeContract.MvpNewsHolder>
            = MvpHomeAdapter(presenter, this)

    override fun onSetRecyclerSpan(): Int = 1
    override val layoutId: Int = R.layout.fragment_home

    override fun onHolderClick(listPosition: Int, adapterPosition: Int, itemId: String?) {
        fragmentManager?.requestSlideLeftScreen(
            MvpWebFragment(presenter.getItemSpecialValue(listPosition) ?: return).also {
                it.attachFragmentManager(fragmentManager)
            },
            "webView"
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            refresh.setOnClickListener {
                adapter.refresh()
            }
        }
    }

    override fun onHolderLongClick(listPosition: Int, adapterPosition: Int, itemId: String?) {
        AppUtils.copyToClipboard(context!!, presenter.getItemSpecialValue(listPosition) ?: return)
    }

    override fun onHolderAction(listPosition: Int, adapterPosition: Int, itemId: String?, actionType: Int) {
        with(EmptyHolderList.ACTIONS) {
            when(actionType) {
                ACTION_REFRESH -> adapter.refresh()
                else -> adapter.tryAgain()
            }
        }
    }

}