package com.riyaz.notes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.room.Query
import androidx.room.Update
import com.riyaz.notes.data.dao.TopicDao
import com.riyaz.notes.data.entety.Note
import com.riyaz.notes.data.entety.Step
import com.riyaz.notes.data.entety.Topic
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TopicRepository(private val topicDao: TopicDao): TopicRepositoryInterface {

    override suspend fun insertTopic(topic: Topic){
        topicDao.addTopic(topic)
    }

    override fun getAllTopics(): LiveData<List<Topic>>{
        return topicDao.getAllTopics().asLiveData()
    }

    override suspend fun deleteTopic(topic: Topic){
        topicDao.deleteTopic(topic)
    }

    override fun getTopic(id: Int): Flow<Topic>{
        return topicDao.getTopic(id)
    }

    override suspend fun addStep(id: Int, steps: List<Step>){
        topicDao.addStep(id, steps)
    }

    override suspend fun addNote(id: Int, notes: List<Note>){
        topicDao.addNote(id, notes)
    }

    override fun getSearchedTopic(query: String): Flow<List<Topic>>{
        return topicDao.getSearchTopics(query)
    }
}