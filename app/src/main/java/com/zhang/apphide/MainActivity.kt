package com.zhang.apphide

import android.Manifest
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager.GET_UNINSTALLED_PACKAGES
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener
import com.zhang.apphide.BuildConfig
import com.zhang.apphide.R
import com.zhang.apphide.databinding.ActivityMainBinding
import com.zhang.apphide.model.AppInfo

import com.zhang.apphide.adapter.AppAdapter
import com.zhang.apphide.config.Settings
import com.zhang.apphide.extension.getFavorite
import com.zhang.apphide.service.HiddenServiceNoRoot
import eu.chainfire.libsuperuser.Shell

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val appAdapter = AppAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        initPermissions()

        setupRecyclerView()

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                populateAppList(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        val packageName = "com.wtyt.lggcb.diaodu"

        val cmd = "pm ${if (true) "hide" else "unhide"} $packageName"
//            HiddenServiceNoRoot.performAction(cmd)
        val s = Shell.SH.run("ps")
        Handler().postDelayed({
            Log.e("zhangshi:", "Shell.SH.run(cmd)" + s)
        }, 5000)
    }

    fun setupRecyclerView() {
        binding.appRecycler.layoutManager = LinearLayoutManager(this)
        binding.appRecycler.adapter = appAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent()
                intent.setClass(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun initPermissions() {
        val snackBarPermissionListener = SnackbarOnDeniedPermissionListener.Builder
            .with(binding.root as ViewGroup, "需要电话权限以便从拨号盘启动")
            .withOpenSettingsButton("设置")
            .build()
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.PROCESS_OUTGOING_CALLS)
            .withListener(snackBarPermissionListener)
            .check()
    }

    fun populateAppList(flag: Int) {
        val pm = packageManager
        val installedApps = pm.getInstalledApplications(0)

        appAdapter.setNewData(
            when (flag) {
                Settings.SPINNER_STAR_APP -> installedApps.filter { it.packageName.getFavorite() }
                Settings.SPINNER_HIDDEN_APP -> installedApps.filter { !it.enabled }
                else -> installedApps
            }
                .filter { it.packageName != BuildConfig.APPLICATION_ID && it.flags and ApplicationInfo.FLAG_SYSTEM != 1 }
                .fold(mutableListOf(), { newData, applicationInfo ->
                    newData.add(AppInfo(applicationInfo))
                    newData
                })
        )

        Shell.SH.run("pm hide com.wtyt.lggcb.diaodu")
    }

    fun populateAppList2(flag: Int) {
        val pm = packageManager
        val installedApps = pm.getInstalledApplications(0)
        Log.e("zhangshi:", installedApps.size.toString())
        val packages = pm.getInstalledPackages(0)
        Log.e("zhangshi:", packages.size.toString())


        val newData = mutableListOf<AppInfo>()
        for (i in installedApps) {
            newData.add(AppInfo(i))
        }
        appAdapter.setNewData(newData)
    }

}
