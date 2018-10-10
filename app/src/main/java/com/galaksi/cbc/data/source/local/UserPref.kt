package com.galaksi.cbc.data.source.local

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.galaksi.cbc.App

/**
 * Created by rahmatrasyidi on 12/08/18.
 */

object UserPref {
    private val PREFS_FILENAME = App.INSTANCE.packageName + "USER_PREF"
    private val IS_LOGIN = "isLogin"
    private val prefs: SharedPreferences = App.INSTANCE.getSharedPreferences(PREFS_FILENAME, MODE_PRIVATE)

    var isLogin: Boolean
        get() = prefs.getBoolean(IS_LOGIN, false)
        set(value) = prefs.edit().putBoolean(IS_LOGIN, value).apply()
}