package com.zhang.apphide.extension

import com.zhang.apphide.config.SharedPreferenceConfig.PREFIX_PREF_KEY_B_FAVORITE
import com.zhang.apphide.config.SharedPreferenceConfig.PREFIX_PREF_KEY_B_HIDDEN
import com.zhang.apphide.config.SharedPreferenceConfig.PREFIX_PREF_KEY_S_PASSWORD
import com.zhang.apphide.config.SharedPreferenceConfig.sp

fun String.getFavorite(): Boolean = sp.getBoolean("$PREFIX_PREF_KEY_B_FAVORITE$this", false)
fun String.saveFavorite(favorite: Boolean) {
    sp.edit {
        putBoolean("$PREFIX_PREF_KEY_B_FAVORITE${this@saveFavorite}", favorite)
    }
}

fun String.getHidden(): Boolean = sp.getBoolean("$PREFIX_PREF_KEY_B_HIDDEN$this", false)
fun String.saveHidden(hidden: Boolean) {
    sp.edit {
        putBoolean("$PREFIX_PREF_KEY_B_HIDDEN${this@saveHidden}", hidden)
    }
}

fun String.getPassword(): String? = sp.getString("$PREFIX_PREF_KEY_S_PASSWORD$this", "")
fun String.savePassword(password: String) {
    sp.edit {
        putString("$PREFIX_PREF_KEY_S_PASSWORD${this@savePassword}", password)
    }
}
