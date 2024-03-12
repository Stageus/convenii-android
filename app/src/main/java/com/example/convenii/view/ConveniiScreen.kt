package com.example.convenii.view

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.convenii.R
import com.example.convenii.view.account.StartScreen

enum class ConveniiScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
}

@Composable
fun ConveniiApp(
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ConveniiScreen.Start.name,
    ) {

        composable(ConveniiScreen.Start.name) {
            StartScreen()
        }
    }
}