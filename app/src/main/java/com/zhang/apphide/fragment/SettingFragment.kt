package com.zhang.apphide.fragment

import android.content.ComponentName
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceFragment
import android.widget.Toast
import com.zhang.apphide.R
import com.zhang.apphide.AppApplication
import com.zhang.apphide.MainActivity
import com.zhang.apphide.ProxyActivity

import com.zhang.apphide.config.Settings

class SettingFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {
    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == Settings.KEY_PREF_INVISIBLE) {
            val selfInvisible = sharedPreferences.getBoolean(key, false)
            val p = activity.packageManager
            val componentName = ComponentName(activity, ProxyActivity::class.java)
            when (selfInvisible) {
                true -> {
                    Toast.makeText(AppApplication.get(), "程序将在3s后退出，退出后请从拨号盘进入", Toast.LENGTH_LONG).show()
                    handler.postDelayed({
                        p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 0)
                    }, 3000)
                }
                false -> p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)
            }
        }
    }
}
