package com.tman.quizzle.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tman.quizzle.R
import com.tman.quizzle.navigation.Screen

@Composable
fun LandingScreen(
    navController: NavController,
    viewModel: LandingViewModel = viewModel()
) {
    val mContext = LocalContext.current
    val buttonList by remember {
        mutableStateOf(arrayListOf(
            ScreenType.MUSIC, ScreenType.TV, ScreenType.SPORTS, ScreenType.HIGHSCORE
        ))
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = mContext.resources.getString(R.string.landing_screen_title),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                items = buttonList,
                itemContent = {
                    LandingScreenListItem(it, navController)
                })
        }
    }
}

@Composable
fun LandingScreenListItem(screenType: ScreenType, navController: NavController) {
    var sizeState by remember { mutableStateOf(16.dp) }
    val size by animateDpAsState(targetValue = sizeState)

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable(onClick = {
                sizeState = 0.dp
                when (screenType) {
                    ScreenType.MUSIC -> navController.navigate(Screen.GameScreen.withArgs(GameType.MUSIC.type))
                    ScreenType.TV -> navController.navigate(Screen.GameScreen.withArgs(GameType.TV.type))
                    ScreenType.SPORTS -> navController.navigate(Screen.GameScreen.withArgs(GameType.SPORTS.type))
                    ScreenType.HIGHSCORE -> navController.navigate(Screen.HighScoreScreen.route)
                }
            }),
        elevation = 2.dp,
        backgroundColor = Color(0xFF6200EE),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row {
            Column {
                Text(
                    text = screenType.type,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = size, bottom = size)
                )
            }
        }
    }
}

enum class ScreenType(val type: String) {
    MUSIC("Music"),
    TV("Television/Film"),
    SPORTS("Sports"),
    HIGHSCORE("High Score")
}