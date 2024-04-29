package com.example.convenii

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.convenii.ui.theme.ConveniiTheme
import com.example.convenii.view.ConveniiApp
import com.example.convenii.viewModel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: SplashViewModel by viewModels()

    @SuppressLint("StateFlowValueCalledInComposition")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            !viewModel.isReady.value
        }

        setContent {
            val startDestination by viewModel.screen.collectAsState()
            ConveniiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = Color.White,

                    ) {
                    if (startDestination.isNotEmpty()) {
                        ConveniiApp(startDestination)
                    }
                }
            }
        }
    }
}


