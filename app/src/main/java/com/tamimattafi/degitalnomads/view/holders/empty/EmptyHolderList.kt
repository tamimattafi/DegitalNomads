package com.tamimattafi.degitalnomads.view.holders.empty

import android.content.Context
import com.tamimattafi.degitalnomads.R

object EmptyHolderList {

    object ACTIONS {
        const val ACTION_REFRESH = 0
        const val ACTION_TRY_AGAIN = 1
    }

    const val EMPTY_LIST = 0
    const val NO_CONNECTION = 1
    const val TRY_AGAIN = 2

    private var EMPTY_HOLDER_LIST : ArrayList<EmptyHolderData>? = null

    private fun getList(context: Context) : ArrayList<EmptyHolderData> {
        with(context.resources) {
            return EMPTY_HOLDER_LIST ?: ArrayList<EmptyHolderData>()
                .apply {
                    add(
                        EmptyHolderData(
                            EMPTY_LIST,
                            R.drawable.placeholder_empty,
                            getString(R.string.no_data),
                            getString(R.string.no_data_was_loaded),
                            ACTIONS.ACTION_REFRESH,
                            getString(R.string.refresh)
                        )
                    )
                    add(
                        EmptyHolderData(
                            NO_CONNECTION,
                            R.drawable.placeholder_error,
                            getString(R.string.no_connection),
                            getString(R.string.your_device_not_connected),
                            ACTIONS.ACTION_REFRESH,
                            context.getString(R.string.refresh)
                        )
                    )
                    add(
                        EmptyHolderData(
                            TRY_AGAIN,
                            R.drawable.placeholder_error,
                            getString(R.string.error_loading_data),
                            getString(R.string.something_went_wrong_check_internet),
                            ACTIONS.ACTION_TRY_AGAIN,
                            getString(R.string.try_again)
                            )
                    )
                }
                .also { EMPTY_HOLDER_LIST = it }
        }
    }

    fun getItem(context: Context, itemId : Int) : EmptyHolderData =
            getList(context)[itemId]
}