package com.riyaz.notes.di

import android.content.Context
import androidx.room.Room
import com.riyaz.notes.core.Constant.TOPIC_DATABASE_NAME
import com.riyaz.notes.data.database.TopicDatabase
import com.riyaz.notes.data.entety.Topic
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun getDefaultDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, TopicDatabase::class.java, TOPIC_DATABASE_NAME).build()

    @Provides
    fun getDefaultDatabaseDao(
        database: TopicDatabase
    ) = database.topicDao()

}