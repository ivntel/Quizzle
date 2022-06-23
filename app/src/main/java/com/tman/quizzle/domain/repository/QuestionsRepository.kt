package com.tman.quizzle.domain.repository

import com.tman.quizzle.domain.model.Questions

interface QuestionsRepository {
    fun getQuestions(): Questions?
}