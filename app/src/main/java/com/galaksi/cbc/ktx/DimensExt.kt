package com.galaksi.cbc.ktx

import android.content.res.Resources

/**
 * Created by rahmatrasyidi on 12/08/18.
 */

fun Int.toDp(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
