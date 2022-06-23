package com.tman.quizzle.navigation

sealed class Screen(val route: String) {
    object LandingScreen: Screen("landing_screen")
    object GameScreen: Screen("game_screen")
    object HighScoreScreen: Screen("high_score_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}