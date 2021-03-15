package com.zhang.apphide.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager

import com.zhang.apphide.MainActivity
import com.zhang.apphide.config.Settings
import com.zhang.apphide.ProxyActivity
class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        var phoneNumber: String? = resultData
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val launchPassword = preferences.getString(Settings.KEY_PREF_LAUNCH_PASSWORD, "#1234")
        if (phoneNumber == null) {
            phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
        }

        if (phoneNumber == launchPassword) {
            val i = Intent()
            i.setClassName("com.zhang.apphide", "com.zhang.apphide.MainActivity")
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
            resultData = null
        } else {
            //查询preference
        }

    }
}
