package com.tamimattafi.degitalnomads.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    const val SERVER_DATE_PATTERN: String = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val UI_DATE_PATTERN: String = "EEE, dd MMMM yyyy, HH:mm"

    private fun toString(date: Date?, pattern: String): String {
        return if (date != null) {
            SimpleDateFormat(pattern, Locale.getDefault()).format(date)
        } else "～"
    }

    private fun fromString(string: String?, pattern: String): Date? {
        return if (string != null) {
            SimpleDateFormat(pattern, Locale.getDefault()).parse(string)
        } else null
    }

    fun changePattern(firstDate : String, firstPattern: String, secondPattern: String) : String {
        return toString(fromString(firstDate, firstPattern), secondPattern)
    }

}