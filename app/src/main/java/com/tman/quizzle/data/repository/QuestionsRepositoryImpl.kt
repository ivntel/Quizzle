package com.tman.quizzle.data.repository

import android.app.Application
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tman.quizzle.R
import com.tman.quizzle.domain.repository.QuestionsRepository
import com.tman.quizzle.domain.model.Questions
import okio.buffer
import okio.source

class QuestionsRepositoryImpl(private val appContext: Application) : QuestionsRepository {

    @OptIn(ExperimentalStdlibApi::class)
    override fun getQuestions(): Questions? {
        val questionsInputStream = appContext.resources.openRawResource(R.raw.questions)

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val jsonAdapter: JsonAdapter<Questions> = moshi.adapter()

        return jsonAdapter.fromJson(questionsInputStream.source().buffer())
    }
}