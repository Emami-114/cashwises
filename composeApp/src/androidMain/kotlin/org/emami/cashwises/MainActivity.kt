package org.emami.cashwises

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.CompositionLocalProvider
import di.getSharedModules
import org.koin.core.context.startKoin
import ui.App

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        startKoin { modules(getSharedModules()) }
        INSTANCE = this
    }
}

class MainActivity : ComponentActivity() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var ACTIVITY: Activity
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ACTIVITY = this
            CompositionLocalProvider(
//                LocalImageLoader provides remember { generateImageLoader() },
            ) {
                App()
//            }
            }
        }
    }
}
