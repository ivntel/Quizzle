package com.tman.quizzle.domain

import com.tman.quizzle.ui.ScreenType
import kotlinx.coroutines.flow.Flow

interface AppDataStore {
    suspend fun getHighScore(screenType: ScreenType?): Flow<Int>
    suspend fun saveHighScore(highScore: Int, screenType: ScreenType?)
}