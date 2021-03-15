package com.zhang.apphide.service

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.zhang.apphide.AppApplication

import eu.chainfire.libsuperuser.Shell
import java.io.BufferedReader
import java.io.InputStreamReader

class HiddenServiceNoRoot : IntentService("hidden-service") {
    val session: Shell.Interactive by lazy {
        Shell.Builder()
                .useSH()
                .setWantSTDERR(true)
                .setOnSTDOUTLineListener { output ->
                    Log.d("evil", "output = $output")
                }
                .setOnSTDERRLineListener { error ->
                    Log.d("evil", "error = $error")
                }
                .open { commandCode, exitCode, output ->
                    output.forEach {
                        Log.d("evil", "result = $it")
                    }
                }
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            val action = it.action
            if (!action.isNullOrEmpty()) {
                Log.d("evil", "action = $action")
//                Shell.SH.run(action)
//                session.addCommand(action)
                val output = StringBuffer()
                try {
                    val p = Runtime.getRuntime().exec(action)
                    p.waitFor()
                    val reader = BufferedReader(InputStreamReader(p.inputStream))
                    var line = reader.readLine()
                    while (line != null) {
                        output.append(line + "\n")
                        line = reader.readLine()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val response = output.toString()
                Log.d("evil", "output = $response")
            }
        }
    }

    companion object {
        fun performAction(action: String) {
            val svc = Intent(AppApplication.get(), HiddenServiceNoRoot::class.java)
            svc.action = action
            AppApplication.get().startService(svc)
        }
    }

}
