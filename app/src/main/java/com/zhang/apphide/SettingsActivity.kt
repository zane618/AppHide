package com.zhang.apphide

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zhang.apphide.R
import com.zhang.apphide.databinding.ActivitySettingsBinding

import com.zhang.apphide.fragment.SettingFragment

class SettingsActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivitySettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, SettingFragment())
                .commit()
    }
}
