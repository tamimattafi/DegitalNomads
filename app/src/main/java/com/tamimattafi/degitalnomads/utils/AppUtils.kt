package com.tamimattafi.degitalnomads.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import android.content.ClipData
import android.content.ClipboardManager
import com.tamimattafi.degitalnomads.R


object AppUtils {

    fun getDrawable(context: Context, drawableId : Int) : Drawable? {
       return ResourcesCompat.getDrawable(context.resources, drawableId, null)
    }

    fun showToast(context: Context, text : String? = null) {
        Toast.makeText(context, text ?:  context.resources.getString(R.string.something_went_wrong), Toast.LENGTH_LONG)
            .show()
    }

    fun copyToClipboard(context : Context, text : String) {
        (context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?)
            ?.apply {
                setPrimaryClip(ClipData.newPlainText(text, text))
                showToast(context, "Link Copied To Clipboard")
            }
    }



}