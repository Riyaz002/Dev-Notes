package com.riyaz.notes.repository

import androidx.room.Query
import androidx.room.Update
import com.riyaz.notes.data.dao.TopicDao
import com.riyaz.notes.data.entety.Note
import com.riyaz.notes.data.entety.Step
import com.riyaz.notes.data.entety.Topic
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TopicRepository(private val topicDao: TopicDao) {

    val topics: Flow<List<Topic>> = topicDao.getAllTopics()

    suspend fun insertTopic(topic: Topic){
        topicDao.addTopic(topic)
    }

    suspend fun deleteTopic(topic: Topic){
        topicDao.deleteTopic(topic)
    }

    fun getTopic(id: Int): Flow<Topic>{
        return topicDao.getTopic(id)
    }

    suspend fun addStep(id: Int, steps: List<Step>){
        topicDao.addStep(id, steps)
    }

    suspend fun addNote(id: Int, notes: List<Note>){
        topicDao.addNote(id, notes)
    }


    fun getSearchedTopic(query: String): Flow<List<Topic>>{
        return topicDao.getSearchTopics(query)
    }
}