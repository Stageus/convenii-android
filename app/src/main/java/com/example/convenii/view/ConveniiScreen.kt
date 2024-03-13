package com.example.convenii.view

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.convenii.view.account.SignInScreen
import com.example.convenii.view.account.StartScreen

enum class ConveniiScreen() {
    Start,
    SignIn

}

@Composable
fun ConveniiApp(
) {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ConveniiScreen.Start.name,
    ) {
        composable(route = ConveniiScreen.Start.name) {
            StartScreen(
                navController = navController
            )
        }

        composable(
            route = ConveniiScreen.SignIn.name,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(400)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400)
                )
            }
        ) {
            SignInScreen()
        }
    }
}