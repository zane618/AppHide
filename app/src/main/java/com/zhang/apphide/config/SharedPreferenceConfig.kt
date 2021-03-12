package com.zhang.apphide.config

import android.content.Context
import android.content.SharedPreferences
import com.zhang.apphide.AppApplication

object SharedPreferenceConfig {
    val FILE_NAME = "com.zhang.apphide_app"

    val PREFIX_PREF_KEY_B_FAVORITE = "favorite_"
    val PREFIX_PREF_KEY_B_HIDDEN = "hidden_"
    val PREFIX_PREF_KEY_S_PASSWORD = "password_"

    val sp: SharedPreferences by lazy {
        AppApplication.get()
                .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }
}
