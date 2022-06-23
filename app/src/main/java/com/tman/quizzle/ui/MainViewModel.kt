package com.tman.quizzle.ui

import com.tman.quizzle.domain.repository.QuestionsRepository
import com.tman.quizzle.domain.model.Questions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val questionsRepository: QuestionsRepository) : BaseViewModel() {

    fun getQuestions(): Questions? = questionsRepository.getQuestions()
}