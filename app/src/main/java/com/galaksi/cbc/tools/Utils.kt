package com.galaksi.cbc.tools

import android.graphics.Point
import android.util.DisplayMetrics
import android.os.Build
import android.view.WindowManager



/**
 * Created by rahmatrasyidi on 12/08/18.
 */

object Utils {

    fun getDisplaySize(windowManager: WindowManager): Point {
        try {
            if (Build.VERSION.SDK_INT > 16) {
                val display = windowManager.defaultDisplay
                val displayMetrics = DisplayMetrics()
                display.getMetrics(displayMetrics)
                return Point(displayMetrics.widthPixels, displayMetrics.heightPixels)
            } else {
                return Point(0, 0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Point(0, 0)
        }
    }
}