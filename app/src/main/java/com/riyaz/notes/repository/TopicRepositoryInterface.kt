package com.riyaz.notes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.riyaz.notes.data.entety.Note
import com.riyaz.notes.data.entety.Step
import com.riyaz.notes.data.entety.Topic
import kotlinx.coroutines.flow.Flow

interface TopicRepositoryInterface {

    suspend fun insertTopic(topic: Topic)

    fun getAllTopics(): LiveData<List<Topic>>

    suspend fun deleteTopic(topic: Topic)

    fun getTopic(id: Int): Flow<Topic>

    suspend fun addStep(id: Int, steps: List<Step>)
    suspend fun addNote(id: Int, notes: List<Note>)
    fun getSearchedTopic(query: String): Flow<List<Topic>>
}