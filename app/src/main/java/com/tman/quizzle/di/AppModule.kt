package com.tman.quizzle.di

import android.app.Application
import com.tman.quizzle.data.AppDataStoreImpl
import com.tman.quizzle.data.repository.QuestionsRepositoryImpl
import com.tman.quizzle.domain.AppDataStore
import com.tman.quizzle.domain.repository.QuestionsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuestionsRepository(app: Application): QuestionsRepository {
        return QuestionsRepositoryImpl(app)
    }

    @Provides
    @Singleton
    fun provideAppDataStore(app: Application): AppDataStore {
        return AppDataStoreImpl(app)
    }
}