package com.tman.quizzle.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.tman.quizzle.domain.AppDataStore
import com.tman.quizzle.domain.model.Question
import com.tman.quizzle.domain.repository.QuestionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val questionsRepository: QuestionsRepository,
    private val appDataStore: AppDataStore,
) : BaseViewModel() {
    var uiState by mutableStateOf(GameUiState())
        private set

    var highScore = 0
    private var screenType: ScreenType? = null

    fun setUiState(gameType: String) {
        uiState.questionsList = questionsRepository.getQuestions()?.questions?.filter { it.type == gameType }!!
        uiState.gameType = gameType
        setCurrentQuestion(initialize = true)

        screenType =
            when (uiState.gameType) {
                GameType.MUSIC.type -> ScreenType.MUSIC
                GameType.TV.type -> ScreenType.TV
                GameType.SPORTS.type -> ScreenType.SPORTS
                else -> null
            }

        viewModelScope.launch(Dispatchers.IO) {
            appDataStore.getHighScore(screenType).collectLatest {
                highScore = it
            }
        }
    }

    fun setCurrentQuestion(initialize: Boolean = false) {
        if (!initialize) uiState.currentQuestionsIndex++
        uiState.currentQuestion = uiState.questionsList[uiState.currentQuestionsIndex]
        uiState.currentQuestionOptions = arrayListOf(
            uiState.currentQuestion?.answer_1.orEmpty(),
            uiState.currentQuestion?.answer_2.orEmpty(),
            uiState.currentQuestion?.answer_3.orEmpty(),
            uiState.currentQuestion?.answer_4.orEmpty()
        ).shuffled() as ArrayList<String>
    }

    fun saveHighScore() = viewModelScope.launch(Dispatchers.IO) {
        if (uiState.currentQuestionsIndex > highScore) {
            appDataStore.saveHighScore(uiState.currentQuestionsIndex, screenType)
        }
    }
}

data class GameUiState(
    var gameType: String = "",
    var currentQuestionsIndex: Int = 0,
    var questionsList: List<Question> = emptyList(),
    var currentQuestion: Question? = Question("", "", "", "", "", "", "", ""),
    var currentQuestionOptions: ArrayList<String> = arrayListOf(),
    var isButtonClicked: Boolean = false
)