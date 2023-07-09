package xyz.hanabinoir.photoviewerxyzen.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import xyz.hanabinoir.photoviewerxyzen.ui.screen.DetailScreen
import xyz.hanabinoir.photoviewerxyzen.ui.screen.HomeScreen

sealed class NavScreen(val route: String) {
    object Home: NavScreen(route = "home_screen")
    object Detail: NavScreen(route = "detail_screen")
}

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavScreen.Home.route
    ) {
        composable(
            route = NavScreen.Home.route
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = "${NavScreen.Detail.route}?selectedPhoto={url}&description={alt}",
            arguments = listOf(
                navArgument("url") {},
                navArgument("alt") {}
            )
        ) {
            DetailScreen(
                it.arguments?.getString("url"),
                it.arguments?.getString("alt")
            )
        }
    }
}