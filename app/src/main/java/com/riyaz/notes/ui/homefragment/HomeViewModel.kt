package com.riyaz.notes.ui.homefragment

import androidx.lifecycle.*
import com.riyaz.notes.data.entety.Topic
import com.riyaz.notes.repository.TopicRepository
import kotlinx.coroutines.launch

class HomeViewModel(val repository: TopicRepository) : ViewModel() {

    val allTopics: LiveData<List<Topic>> = repository.topics.asLiveData()

    fun persistNewTopic(title: String, description: String){
        val topic= Topic(title,description)
        viewModelScope.launch {
            repository.insertTopic(topic)
        }
    }

//    fun deleteTopic(topic: Topic){
//        viewModelScope.launch {
//            repository.deleteTopic(topic)
//        }
//    }

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