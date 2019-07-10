package com.tamimattafi.degitalnomads.mvp

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tamimattafi.degitalnomads.R
import com.tamimattafi.degitalnomads.utils.AppUtils
import com.tamimattafi.degitalnomads.view.custom.ItemOffsetDecoration
import com.tamimattafi.degitalnomads.view.fragments.global.BaseFragment
import kotlinx.android.synthetic.main.item_recycler_refresher.*

abstract class MvpRecyclerFragment<HOLDER : MvpRecyclerContract.MvpHolder> : BaseFragment(),
    MvpRecyclerContract.MvpView<HOLDER>,
    MvpRecyclerContract.MvpFilterListener {

    abstract fun onSetUpPresenter(): MvpRecyclerContract.MvpPresenter<HOLDER>
    abstract fun onSetUpAdapter(): MvpRecyclerAdapter<HOLDER>
    abstract fun onSetRecyclerSpan(): Int

    protected lateinit var presenter: MvpRecyclerContract.MvpPresenter<HOLDER>
    protected lateinit var adapter: MvpRecyclerAdapter<HOLDER>


    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = onSetUpPresenter()
        adapter = onSetUpAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            with(adapter) {
                refreshLayout.setOnRefreshListener { refresh() }
                setUpRecycler()
                if (listItemCount == 0) {
                    setRefreshing(true)
                    startListening()
                }
            }
        }
    }

    override fun onRequestChangeFilter() {}

    open fun setUpRecycler() {
        with(recyclerView) {
            layoutManager = GridLayoutManager(
                context!!,
                onSetRecyclerSpan(),
                getRecyclerOrientation(),
                false
            ).also { setUpLayoutManagerSpan(it) }
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(onSetRecyclerDecorator() ?: return)
            addRecyclerScrollListener(this)
            adapter = this@MvpRecyclerFragment.adapter
        }
    }

    open fun getRecyclerOrientation(): Int = RecyclerView.VERTICAL

    open fun addRecyclerScrollListener(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    with(adapter) {
                        if (dy > 0 && !isLoading && !allData) {
                            (recyclerView.layoutManager as? GridLayoutManager)?.apply {
                                if (findLastVisibleItemPosition() >= itemCount * 90 / 100) {
                                    loadMoreData()
                                }
                            }
                        }
                    }
                }
            }
        )
    }


    open fun onSetRecyclerDecorator(): RecyclerView.ItemDecoration? =
        ItemOffsetDecoration(context!!, R.dimen.item_spacing)

    open fun setUpLayoutManagerSpan(layoutManager: GridLayoutManager) {
        with(layoutManager) {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when {
                        itemCount == 1 -> spanCount
                        position in 0 until this@MvpRecyclerFragment.adapter.headersCount -> {
                            spanCount
                        }
                        position >= this@MvpRecyclerFragment.adapter.listItemCount + this@MvpRecyclerFragment.adapter.headersCount -> spanCount
                        else -> 1
                    }
                }
            }
        }
    }

    override fun setRefreshing(refreshing: Boolean) {
        try {
            with(refreshLayout) {
                if (isRefreshing != refreshing) {
                    isRefreshing = refreshing
                }
                hideRecycler(refreshing)
            }
        } catch (e: Exception) {
        }
    }

    private fun hideRecycler(hide: Boolean) {
        with(recyclerView) {
            visibility = if (hide && visibility == View.VISIBLE) {
                View.INVISIBLE
            } else if (!hide && visibility == View.INVISIBLE) {
                View.VISIBLE
            } else return@with
        }
    }

    override fun showError(message: String) {
        AppUtils.showToast(context!!, message)
    }

    override fun onDestroyView() {
        presenter.onDestroyView()
        super.onDestroyView()
    }

    override fun onPause() {
        presenter.onDestroyView()
        super.onPause()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}