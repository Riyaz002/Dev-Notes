package com.riyaz.notes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.riyaz.notes.data.entety.Note
import com.riyaz.notes.data.entety.Step
import com.riyaz.notes.data.entety.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf

class FakeTopicRepository: TopicRepositoryInterface {

    val list = mutableListOf<Topic>()
    val liveList = MutableLiveData(list)

    fun updateLiveTopicList(){
        liveList.postValue(list)
    }

    override suspend fun insertTopic(topic: Topic){
        list.add(topic)
        updateLiveTopicList()
    }

    override fun getAllTopics(): LiveData<List<Topic>> {
        return liveList.asFlow().asLiveData()
    }

    override suspend fun deleteTopic(topic: Topic){
        list.remove(topic)
        updateLiveTopicList()
    }

    override fun getTopic(id: Int): Flow<Topic> {
        return list.filter { it.id == id }.asFlow()
    }

    override suspend fun addStep(id: Int, steps: List<Step>){
        list.first { it.id == id }.steps = steps
        updateLiveTopicList()
    }

    override suspend fun addNote(id: Int, notes: List<Note>){
        list.first { it.id == id }.notes = notes
        updateLiveTopicList()
    }

    override fun getSearchedTopic(query: String): Flow<List<Topic>> {
        return flowOf(list.filter { it.title.matches(Regex(query)) })
    }
}