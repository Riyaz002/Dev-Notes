package com.riyaz.notes.repository

import androidx.room.Query
import androidx.room.Update
import com.riyaz.notes.data.dao.TopicDao
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

    suspend fun updateTopic(topic: Topic){
        topicDao.updateTopic(topic)
    }

    suspend fun getTopic(title: String): Topic{
        return topicDao.getTopic(title)
    }

//    fun getSteps(title: String): Flow<List<Step>>{
//        return topicDao.getSteps(title)
//    }

    suspend fun addStep(title: String, step: MutableList<Step>){
        topicDao.addStep(title, step)
    }


    fun getSearchedTopic(query: String): Flow<List<Topic>>{
        return topicDao.getSearchTopics(query)
    }
}