package com.galaksi.cbc.util

import android.provider.Settings
import com.galaksi.cbc.App
import java.util.*

object AppUtils {
    val uniquePsuedoID: String
        get() {
            val android_id = Settings.Secure.getString(App.INSTANCE.contentResolver,
                    Settings.Secure.ANDROID_ID)
            return if (android_id != null) android_id else {
                UUID.randomUUID().toString()
            }
        }
}