package com.tman.quizzle.data

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tman.quizzle.domain.AppDataStore
import com.tman.quizzle.ui.ScreenType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppDataStoreImpl(private val appContext: Application) : AppDataStore {
    private val PREFERENCES_HIGH_SCORES = "prefs_high_scores"

    private val Context.prefsDataStore by preferencesDataStore(
        name = PREFERENCES_HIGH_SCORES
    )

    object PreferenceKeys {
        val TV_HIGH_SCORE = intPreferencesKey(ScreenType.TV.type)
        val MUSIC_HIGH_SCORE = intPreferencesKey(ScreenType.MUSIC.type)
        val SPORTS_HIGH_SCORE = intPreferencesKey(ScreenType.SPORTS.type)
    }

    override suspend fun getHighScore(screenType: ScreenType?): Flow<Int> {
        return appContext.prefsDataStore.data
            .map { preferences ->
                when (screenType?.type) {
                    ScreenType.TV.type -> preferences[PreferenceKeys.TV_HIGH_SCORE] ?: 0
                    ScreenType.MUSIC.type -> preferences[PreferenceKeys.MUSIC_HIGH_SCORE] ?: 0
                    ScreenType.SPORTS.type -> preferences[PreferenceKeys.SPORTS_HIGH_SCORE] ?: 0
                    else -> 0
                }
            }
    }

    override suspend fun saveHighScore(highScore: Int, screenType: ScreenType?) {
        appContext.prefsDataStore.edit { preferences ->
            when (screenType?.type) {
                ScreenType.TV.type -> preferences[PreferenceKeys.TV_HIGH_SCORE] = highScore
                ScreenType.MUSIC.type -> preferences[PreferenceKeys.MUSIC_HIGH_SCORE] = highScore
                ScreenType.SPORTS.type -> preferences[PreferenceKeys.SPORTS_HIGH_SCORE] = highScore
            }
        }
    }
}