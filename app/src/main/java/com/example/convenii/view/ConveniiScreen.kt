package com.example.convenii.view

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.convenii.R
import com.example.convenii.view.account.SignInScreen
import com.example.convenii.view.account.StartScreen
import com.example.convenii.view.account.register.Register1Screen
import com.example.convenii.view.account.register.Register2Screen
import com.example.convenii.view.account.register.Register3Screen
import com.example.convenii.view.account.register.Register4Screen
import com.example.convenii.view.detail.ProductDetailScreen
import com.example.convenii.view.main.home.HomeScreen

enum class ConveniiScreen(val route: String, val title: String, val icon: Int? = null) {
    Start("start", "시작", R.drawable.icon_home),
    SignIn("signIn", "로그인", R.drawable.icon_home),
    Register1("register1", "회원가입", R.drawable.icon_home),
    Register2("register2", "회원가입"),
    Register3("register3", "회원가입"),
    Register4("register4", "회원가입"),
    Home("home", "홈", R.drawable.icon_home),
    ProductDetail("productDetail", "상품 상세"),

}

fun NavGraphBuilder.composableWithAnimation(
    route: String,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            )

        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            )
        },
        content = content

    )
}

@Composable
fun ConveniiApp(
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ConveniiScreen.ProductDetail.name,
    ) {
        composable(route = ConveniiScreen.Start.name) {
            StartScreen(
                navController = navController
            )
        }
        composableWithAnimation(route = ConveniiScreen.SignIn.name) {
            SignInScreen(
                navController = navController
            )
        }
        composableWithAnimation(route = ConveniiScreen.Register1.name) {
            Register1Screen(
                navController = navController
            )
        }

        composableWithAnimation(route = ConveniiScreen.Register2.name) {
            Register2Screen(
                navController = navController
            )
        }

        composableWithAnimation(route = ConveniiScreen.Register3.name) {
            Register3Screen(
                navController = navController
            )
        }

        composableWithAnimation(route = ConveniiScreen.Register4.name) {
            Register4Screen(
                navController = navController
            )
        }

        composable(route = ConveniiScreen.Home.name) {
            HomeScreen(
                navController = navController
            )
        }

        composable(route = ConveniiScreen.ProductDetail.name) {
            ProductDetailScreen(
                navController = navController
            )
        }
    }
}