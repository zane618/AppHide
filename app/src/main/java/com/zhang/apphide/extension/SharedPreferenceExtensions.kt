package com.zhang.apphide.extension

import android.content.SharedPreferences

fun SharedPreferences.edit(func: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.func()
    editor.apply()
}
