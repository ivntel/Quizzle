package com.tman.quizzle.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Questions(val questions: List<Question>)

@JsonClass(generateAdapter = true)
data class Question(
    val type: String,
    val difficulty: String,
    val question: String,
    val answer_1: String,
    val answer_2: String,
    val answer_3: String,
    val answer_4: String,
    val correct_answer: String
)
