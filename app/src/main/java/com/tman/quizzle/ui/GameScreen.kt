package com.tman.quizzle.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tman.quizzle.utils.showToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@Composable
fun GameScreen(
    navController: NavController,
    gameType: String,
    viewModel: GameViewModel = hiltViewModel()
) {
    viewModel.setUiState(gameType)
    var question by remember { mutableStateOf(viewModel.uiState.currentQuestion) }
    var currentScore by remember { mutableStateOf(viewModel.uiState.currentQuestionsIndex) }
    var optionList by remember { mutableStateOf(viewModel.uiState.currentQuestionOptions) }
    val mContext = LocalContext.current

    fun correctlyAnsweredQuestion() {
        viewModel.setCurrentQuestion()
        question = viewModel.uiState.currentQuestion
        optionList = viewModel.uiState.currentQuestionOptions
        currentScore = viewModel.uiState.currentQuestionsIndex
        if (currentScore > 4) {
            showToast(mContext, "You win")
            viewModel.saveHighScore()
            navController.popBackStack()
            return
        }
        showToast(mContext, "Correct")
    }

    fun incorrectlyAnsweredQuestion() {
        showToast(mContext, "Wrong the correct answer is ${question?.correct_answer.orEmpty()}")
        viewModel.saveHighScore()
        navController.popBackStack()
    }

    BackHandler { viewModel.saveHighScore() }

    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .background(color = Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            border = BorderStroke(2.dp, Color.Blue),
            backgroundColor = Color(0xFF2196F3),
            modifier = Modifier.padding(all = 8.dp)
        ) {
            Text(
                text = question?.question.orEmpty(),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp)
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                items = optionList,
                itemContent = {
                    QuestionListItem(
                        option = it,
                        question?.correct_answer.orEmpty(),
                        { correctlyAnsweredQuestion() },
                        { incorrectlyAnsweredQuestion() }
                    )
                })
        }
        Text(
            text = "Current Score: $currentScore",
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        )
    }
}

@OptIn(ExperimentalTime::class)
@Composable
fun QuestionListItem(
    option: String,
    correctAnswer: String,
    correctlyAnsweredQuestion: () -> Unit,
    incorrectlyAnsweredQuestion: () -> Unit
) {
    var colorState by remember { mutableStateOf(Color(0xFF6200EE)) }
    val color by animateColorAsState(targetValue = colorState)
    // Returns a scope that's cancelled when F is removed from composition
    val coroutineScope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable(onClick = {
                if (option == correctAnswer) {
                    colorState = Color.Green
                    coroutineScope.launch {
                        delay(1.seconds)
                        colorState = Color(0xFF6200EE)
                        correctlyAnsweredQuestion.invoke()
                    }
                } else {
                    colorState = Color.Red
                    coroutineScope.launch {
                        delay(1.seconds)
                        incorrectlyAnsweredQuestion.invoke()
                    }
                }
            }),
        elevation = 2.dp,
        backgroundColor = color,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row {
            Column {
                Text(
                    text = option,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp)
                )
            }
        }
    }
}

enum class GameType(val type: String) {
    MUSIC("music"),
    TV("tv"),
    SPORTS("sports"),
}
