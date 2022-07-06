package com.tman.quizzle.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tman.quizzle.R

@Composable
fun HighScoreScreen(highScoresMap: HashMap<ScreenType, Int>, viewModel: HighScoreViewModel = hiltViewModel()) {
    val screenList by remember { mutableStateOf(viewModel.screenList) }
    val scoresMap by remember { mutableStateOf(highScoresMap) }
    val mContext = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = mContext.resources.getString(R.string.high_score_high_scores),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                items = screenList,
                itemContent = {
                    HighScoreListItem(screenType = it, scoresMap, mContext)
                })
        }
    }
}

@Composable
fun HighScoreListItem(screenType: ScreenType, scoresMap: HashMap<ScreenType, Int>, mContext: Context) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        backgroundColor = Color(0xFF6200EE),
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row {
            Column {
                Text(
                    text = mContext.resources.getString(R.string.high_score_highest_score, screenType.type, scoresMap[screenType].toString()),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp)
                )
            }
        }
    }
}