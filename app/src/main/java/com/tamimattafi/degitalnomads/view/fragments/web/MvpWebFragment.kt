package com.tamimattafi.degitalnomads.view.fragments.web
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.tamimattafi.degitalnomads.R
import com.tamimattafi.degitalnomads.utils.AppUtils
import com.tamimattafi.degitalnomads.view.fragments.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_web.*
import kotlinx.android.synthetic.main.toolbar_web.*

class MvpWebFragment(private val pageUrl : String) : BaseFragment() {
    override val layoutId: Int = R.layout.fragment_web

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {

            setRefreshing(false)

            back.setOnClickListener {
                fragmentManager?.requestBackPress()
            }

            refresh.setOnClickListener {
                setRefreshing(true)
                webView.reload()
            }

            webView.apply {
                @SuppressLint("SetJavaScriptEnabled")
                settings.javaScriptEnabled = true

                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        this@MvpWebFragment.title.text = url
                        copy.apply {
                            setOnClickListener {
                                AppUtils.copyToClipboard(context!!, url ?: return@setOnClickListener)
                            }
                            visibility = View.VISIBLE
                        }
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        setRefreshing(false)
                    }
                }

                loadUrl(pageUrl)
            }

            refreshLayout.setOnRefreshListener {
                setRefreshing(true)
                webView.reload()
            }
        }


    }

    fun setRefreshing(refreshing: Boolean) {
        try {
            with(refreshLayout) {
                if (isRefreshing != refreshing) {
                    isRefreshing = refreshing
                }
                hideWebView(refreshing)
            }
        } catch (e: Exception) {
        }
    }

    private fun hideWebView(hide: Boolean) {
        with(webView) {
            visibility = if (hide && visibility == View.VISIBLE) {
                View.INVISIBLE
            } else if (!hide && visibility == View.INVISIBLE) {
                View.VISIBLE
            } else return@with
        }
    }


}