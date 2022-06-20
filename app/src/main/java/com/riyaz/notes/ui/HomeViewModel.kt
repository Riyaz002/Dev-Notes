package com.riyaz.notes.ui

import androidx.lifecycle.*
import com.riyaz.notes.data.entety.Topic
import com.riyaz.notes.repository.TopicRepository
import kotlinx.coroutines.launch

class HomeViewModel(val repository: TopicRepository) : ViewModel() {
    // TODO: Implement the ViewModel
    val allTopics: LiveData<List<Topic>> = repository.topics.asLiveData()

    fun persistNewTopic(title: String, description: String){
        val topic: Topic = Topic(title,description)
        viewModelScope.launch {
            repository.insertTopic(topic)
        }
    }

    fun deleteTopic(topicTitle: String){
        viewModelScope.launch {
            repository.deleteTopic(topicTitle)
        }
    }
}

class HomeFragmentViewModelFactory(var repository: TopicRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
           return HomeViewModel(repository) as T
        } else throw IllegalArgumentException("ViewModel class is not assignable")
    }

}