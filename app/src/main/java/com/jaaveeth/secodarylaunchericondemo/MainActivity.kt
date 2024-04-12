package com.jaaveeth.secodarylaunchericondemo

import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val preferences: SharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE)
        val value = preferences.getBoolean(
            "key",
            false
        )


        val switch = findViewById<SwitchCompat>(R.id.switch1)
        switch.isChecked = value

        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                changeIconToNew(this)
            } else {
                changeIconToMain(this)
            }

            val editor = preferences.edit()
            editor.putBoolean("key", isChecked)

            editor.apply()

        }
    }

    private fun changeIconToMain(context: Context) {
        try {
            // Get the package manager instance
            val packageManager = context.packageManager

            // Disable the New  component
            val secondLauncherComponent =
                ComponentName(context, "com.jaaveeth.secodarylaunchericondemo.SecondaryActivity")
            packageManager.setComponentEnabledSetting(
                secondLauncherComponent,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )


            // Enable the main activity component
            val firstLauncherComponent =
                ComponentName(context, "com.jaaveeth.secodarylaunchericondemo.MainActivity")
            packageManager.setComponentEnabledSetting(
                firstLauncherComponent,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        } catch (e: java.lang.Exception) {
            Log.e("Exception", e.toString())
        }
    }

    private fun changeIconToNew(context: Context) {
        try {
            val packageManager = context.packageManager

            // Disable the main component
            val firstLauncherComponent =
                ComponentName(context, "com.jaaveeth.secodarylaunchericondemo.MainActivity")
            packageManager.setComponentEnabledSetting(
                firstLauncherComponent,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )

            // Enable the New  component
            val secondLauncherComponent =
                ComponentName(context, "com.jaaveeth.secodarylaunchericondemo.SecondaryActivity")
            packageManager.setComponentEnabledSetting(
                secondLauncherComponent,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        } catch (e: java.lang.Exception) {
            Log.e("Exception", e.toString())
        }
    }
}

