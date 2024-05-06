package com.example.convenii.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.convenii.ui.theme.pretendard
import com.example.convenii.view.ConveniiScreen

@Composable
fun BottomNav(navController: NavController) {
    val items = listOf(
        ConveniiScreen.Home,
        ConveniiScreen.SearchMain,
        ConveniiScreen.Bookmark,
        ConveniiScreen.Profile,
    )


    BottomNavigation(
        backgroundColor = Color.White,
        modifier = Modifier
            .height(72.dp)
            .border(0.5.dp, Color(0xFFA9B0B8)),
    ) {
        val currentRoute = navController.currentDestination?.route
        items.forEach { screen ->
            BottomNavigationItem(
                selected = currentRoute == screen.name,
                onClick = {
                    navController.navigate(screen.name) {
                        launchSingleTop = true // SingleTop
                    }
                },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(15.dp))
                        Icon(
                            painter = painterResource(id = screen.icon!!),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                },
                label = {
                    Text(text = screen.title, fontFamily = pretendard, fontSize = 14.sp)
                },
                selectedContentColor = Color(0xFF28323C),
                unselectedContentColor = Color(0xFFC5C8CE),
            )
        }

    }

}

