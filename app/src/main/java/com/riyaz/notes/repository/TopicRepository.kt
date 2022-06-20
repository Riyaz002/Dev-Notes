package com.riyaz.notes.repository

import com.riyaz.notes.data.dao.TopicDao
import com.riyaz.notes.data.entety.Topic
import kotlinx.coroutines.flow.Flow

class TopicRepository(private val topicDao: TopicDao) {

    val topics: Flow<List<Topic>> = topicDao.getAllTopics()

    suspend fun insertTopic(topic: Topic){
        topicDao.addTopic(topic)
    }

    suspend fun deleteTopic(title: String){
        //topicDao.deleteTopic(title)
    }
}