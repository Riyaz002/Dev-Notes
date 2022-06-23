package com.riyaz.notes.ui.topicdetail

import androidx.lifecycle.*
import com.riyaz.notes.data.entety.Step
import com.riyaz.notes.data.entety.Topic
import com.riyaz.notes.repository.TopicRepository
import kotlinx.coroutines.launch

class TopicDetailViewModel(private val repository: TopicRepository, title: String): ViewModel() {

    private val _topic = MutableLiveData<Topic>()
    val topic: LiveData<Topic> get() = _topic

    init {
        getTopic(title)
    }

    private fun getTopic(title: String) {
        viewModelScope.launch {
            _topic.postValue(repository.getTopic(title))
        }
    }

//    fun getSteps(): LiveData<List<Step>>{
//        return repository.getSteps(topic.value!!.title).asLiveData()
//    }

    fun deleteTopic(){
        viewModelScope.launch {
            topic.value?.let { topic->
                repository.deleteTopic(topic)
            }
        }
    }

    fun addStep(title: String, description: String) {
        val list = topic.value?.steps as MutableList<Step>
        val step = Step(null, title, description)
        list.add(step)

        val topic = Topic(_topic.value!!.title,
            _topic.value!!.description,
            list)

        viewModelScope.launch {
            repository.updateTopic(topic)
        }
    }
}

class TopicDetailViewModelFactory(private val repository: TopicRepository, val title: String): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TopicDetailViewModel::class.java)){
            return TopicDetailViewModel(repository, title) as T
        } else{
            throw IllegalArgumentException("ViewModel class is no assignable")
        }
    }
}