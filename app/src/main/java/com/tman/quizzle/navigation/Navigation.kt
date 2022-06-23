package com.tman.quizzle.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tman.quizzle.ui.GameScreen
import com.tman.quizzle.ui.HighScoreScreen
import com.tman.quizzle.ui.LandingScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LandingScreen.route) {
        composable(route = Screen.LandingScreen.route) { LandingScreen(navController) }
        composable(
            route = Screen.GameScreen.route + "/{gameType}",
            arguments = listOf(
                navArgument(name = "gameType") {
                    type = NavType.StringType
                    defaultValue = "none"
                    nullable = true
                }
            )
        ) { entry ->
            GameScreen(navController, gameType = entry.arguments?.getString("gameType").orEmpty())
        }
        composable(route = Screen.HighScoreScreen.route) { HighScoreScreen(navController) }
    }
}