package com.riyaz.notes.ui

import androidx.lifecycle.*
import androidx.room.Query
import com.riyaz.notes.data.entety.Topic
import com.riyaz.notes.repository.TopicRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

class HomeViewModel(val repository: TopicRepository) : ViewModel() {

    val allTopics: LiveData<List<Topic>> = repository.topics.asLiveData()

    fun persistNewTopic(title: String, description: String){
        val topic: Topic = Topic(title,description)
        viewModelScope.launch {
            repository.insertTopic(topic)
        }
    }

    fun deleteTopic(topic: Topic){
        viewModelScope.launch {
            repository.deleteTopic(topic)
        }
    }
    fun searchTopics(query: String): LiveData<List<Topic>>{
        return repository.getSearchedTopic(query).asLiveData()
    }
}

class HomeFragmentViewModelFactory(var repository: TopicRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
           return HomeViewModel(repository) as T
        } else throw IllegalArgumentException("ViewModel class is not assignable")
    }

}