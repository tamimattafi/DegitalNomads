package com.tamimattafi.degitalnomads.repository.feed

import com.tamimattafi.degitalnomads.model.database.BaseDao
import java.lang.Exception

class MvpCashier<OBJECT>(private val baseDao: BaseDao<OBJECT>) : MvpBackgroundInteraction<List<OBJECT>, Boolean>() {

    override fun doInBackground(vararg p0: List<OBJECT>): Boolean? {
        return try {
            if (isWorking) {
                baseDao.insertAllNews(p0[0])
                true
            } else null
        } catch (e : Exception) {
            null
        }
    }
}