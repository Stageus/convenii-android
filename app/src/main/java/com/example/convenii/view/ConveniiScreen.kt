package com.example.convenii.view

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.example.convenii.view.account.register.Register5Screen
import com.example.convenii.view.detail.ProductDetailScreen
import com.example.convenii.view.detail.ReviewAddScreen
import com.example.convenii.view.detail.ReviewDetailScreen
import com.example.convenii.view.main.home.HomeScreen
import com.example.convenii.view.main.search.SearchMainScreen

enum class ConveniiScreen(val route: String, val title: String, val icon: Int? = null) {
    Start("start", "시작", R.drawable.icon_home),
    SignIn("signIn", "로그인", R.drawable.icon_search),
    Register1("register1", "회원가입", R.drawable.icon_profile),
    Register2("register2", "회원가입"),
    Register3("register3", "회원가입"),
    Register4("register4", "회원가입"),
    Register5("register5", "회원가입"),
    Home("home", "홈", R.drawable.icon_bookmark),
    ProductDetail("productDetail", "상품 상세"),
    ReviewDetail("reviewDetail", "리뷰 상세"),
    ReviewAdd("reviewAdd", "리뷰 작성", R.drawable.icon_search),
    SearchMain("searchMain", "검색", R.drawable.icon_search),

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
    startDestination: String
) {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {


        composable(route = ConveniiScreen.Start.name) {
            StartScreen(
                navController = navController
            )
        }
        composableWithAnimation(route = ConveniiScreen.SignIn.name) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ConveniiScreen.Start.name)
            }
            SignInScreen(
                navController = navController,
                parentEntry = parentEntry
            )
        }
        composableWithAnimation(route = ConveniiScreen.Register1.name) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ConveniiScreen.Start.name)
            }
            Register1Screen(
                navController = navController,
                viewModel = hiltViewModel(parentEntry)
            )
        }

        composableWithAnimation(route = ConveniiScreen.Register2.name) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ConveniiScreen.Start.name)
            }
            Register2Screen(
                navController = navController,
                viewModel = hiltViewModel(parentEntry)

            )
        }

        composableWithAnimation(route = ConveniiScreen.Register3.name) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ConveniiScreen.Start.name)
            }

            Register3Screen(
                navController = navController,
                viewModel = hiltViewModel(parentEntry)

            )
        }

        composableWithAnimation(route = ConveniiScreen.Register4.name) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ConveniiScreen.Start.name)
            }
            Register4Screen(
                navController = navController,
                viewModel = hiltViewModel(parentEntry)

            )
        }

        composableWithAnimation(route = ConveniiScreen.Register5.name) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ConveniiScreen.Start.name)
            }
            Register5Screen(
                navController = navController,
                viewModel = hiltViewModel(parentEntry)
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

        composable(route = ConveniiScreen.ReviewDetail.name) {
            ReviewDetailScreen(
                navController = navController
            )
        }

        composable(route = ConveniiScreen.ReviewAdd.name) {
            ReviewAddScreen(
                navController = navController
            )
        }

        composable(route = ConveniiScreen.SearchMain.name) {
            SearchMainScreen(
                navController = navController
            )
        }
    }
}