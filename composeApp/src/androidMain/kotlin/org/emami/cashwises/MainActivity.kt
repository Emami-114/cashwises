package org.emami.cashwises

import android.app.Application
import ui.App
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp

    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}