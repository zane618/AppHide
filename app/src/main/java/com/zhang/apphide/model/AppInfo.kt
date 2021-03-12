package com.zhang.apphide.model


import android.content.pm.ApplicationInfo
import com.zhang.apphide.extension.*
import com.zhang.apphide.service.HiddenService

class AppInfo(val applicationInfo: ApplicationInfo) {
    val packageName: String
        get() = applicationInfo.packageName

    var favorite: Boolean
        get() = packageName.getFavorite()
        set(value) {
            packageName.saveFavorite(value)
        }
    var hidden: Boolean
        get() = !applicationInfo.enabled
        set(value) {
            val cmd = "pm ${if (value) "disable" else "enable"} $packageName"
            HiddenService.performAction(cmd)
            applicationInfo.enabled = !applicationInfo.enabled
        }
    var password: String?
        get() = packageName.getPassword()
        set(value) {
            value?.let { packageName.savePassword(it) }
        }
}