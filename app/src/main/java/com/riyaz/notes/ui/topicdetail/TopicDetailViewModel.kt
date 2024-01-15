package com.riyaz.notes.ui.topicdetail

import androidx.lifecycle.*
import com.riyaz.notes.core.router.RouteExecutionInterface
import com.riyaz.notes.core.router.StateUpdater
import com.riyaz.notes.data.entety.Step
import com.riyaz.notes.data.entety.Topic
import com.riyaz.notes.repository.TopicRepository
import kotlinx.coroutines.launch
import java.util.HashMap

class TopicDetailViewModel(private val repository: TopicRepository, val id: Int): ViewModel() {

    private val _topic = repository.getTopic(id).asLiveData()
    val topic: LiveData<Topic> get() = _topic

    lateinit var routeHandler: RouteExecutionInterface

    val stateUpdater = object : StateUpdater(){
        override val routeHandler: RouteExecutionInterface
            get() = object : RouteExecutionInterface{
                override fun executePage(pageId: String, parameters: HashMap<String, String>) {
                    this@TopicDetailViewModel.routeHandler.executePage(
                        pageId,
                        parameters
                    )
                }

                override fun executeFunction() {
                    this@TopicDetailViewModel.routeHandler.executeFunction()
                }

            }
    }

    fun deleteTopic(){
        viewModelScope.launch {
            topic.value?.let { topic->
                repository.deleteTopic(topic)
            }
        }
    }

    fun addStep(title: String, description: String) {
        val list: MutableList<Step> = createNewStepList(title, description)

        viewModelScope.launch {
            repository.addStep(topic.value!!.id, list)
        }
    }

    private fun createNewStepList(
        title: String,
        description: String
    ): MutableList<Step> {
        val list: MutableList<Step> = topic.value!!.steps.toMutableList()
        val step = Step(title = title, explanation = description)
        list.add(step)
        return list
    }
}

class TopicDetailViewModelFactory(private val repository: TopicRepository, val id: Int): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TopicDetailViewModel::class.java)){
            return TopicDetailViewModel(repository, id) as T
        } else{
            throw IllegalArgumentException("ViewModel class is no assignable")
        }
    }
}