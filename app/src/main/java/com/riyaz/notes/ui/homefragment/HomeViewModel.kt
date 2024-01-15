package com.riyaz.notes.ui.homefragment

import androidx.lifecycle.*
import com.riyaz.notes.core.router.RouteExecutionInterface
import com.riyaz.notes.core.router.StateUpdater
import com.riyaz.notes.data.entety.Topic
import com.riyaz.notes.repository.TopicRepository
import kotlinx.coroutines.launch
import java.util.HashMap

class HomeViewModel(private val repository: TopicRepository) : ViewModel() {

    val allTopics: LiveData<List<Topic>> = repository.topics.asLiveData()

    lateinit var routeHandler: RouteExecutionInterface

    var stateUpdater = object: StateUpdater(){
        override val routeHandler: RouteExecutionInterface
            get() = object : RouteExecutionInterface{
                override fun executePage(pageId: String, parameters: HashMap<String, String>) {
                    this@HomeViewModel.routeHandler.executePage(
                        pageId,
                        parameters
                    )
                }

                override fun executeFunction() {
                    this@HomeViewModel.routeHandler
                }
            }

    }

    fun persistNewTopic(title: String, description: String){
        val topic= Topic(title,description)
        viewModelScope.launch {
            repository.insertTopic(topic)
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