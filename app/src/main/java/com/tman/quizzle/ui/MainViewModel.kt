package com.tman.quizzle.ui

import androidx.lifecycle.viewModelScope
import com.tman.quizzle.domain.AppDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val appDataStore: AppDataStore) : BaseViewModel() {
    var highScoresMap = hashMapOf<ScreenType, Int>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            appDataStore.getHighScore(ScreenType.SPORTS).collect {
                highScoresMap[ScreenType.SPORTS] = it
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            appDataStore.getHighScore(ScreenType.MUSIC).collect {
                highScoresMap[ScreenType.MUSIC] = it
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            appDataStore.getHighScore(ScreenType.TV).collect {
                highScoresMap[ScreenType.TV] = it
            }
        }
    }
}