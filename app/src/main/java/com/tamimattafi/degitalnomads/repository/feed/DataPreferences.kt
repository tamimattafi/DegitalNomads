package com.tamimattafi.degitalnomads.repository.feed

import android.content.Context
import android.content.SharedPreferences
import com.tamimattafi.degitalnomads.model.database.DatabaseValues

class DataPreferences(context: Context, private val key : String) : MvpFeedContract.MvpDataPreferences {

    private val preferences: SharedPreferences = context.getSharedPreferences(DatabaseValues.APP_PREFERENCES, Context.MODE_PRIVATE)

    override fun getDataSize(): Int = preferences.getInt(key, 0)

    override fun setDataSize(size: Int) {
        with(preferences.edit()) {
            putInt(key, size)
            apply()
        }
    }
}