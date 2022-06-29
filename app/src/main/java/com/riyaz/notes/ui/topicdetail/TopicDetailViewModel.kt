package com.riyaz.notes.ui.topicdetail

import android.util.Log
import androidx.lifecycle.*
import com.riyaz.notes.data.entety.Step
import com.riyaz.notes.data.entety.Topic
import com.riyaz.notes.repository.TopicRepository
import kotlinx.coroutines.launch

class TopicDetailViewModel(private val repository: TopicRepository, title: String): ViewModel() {

    private val _topic = MutableLiveData<Topic>()
    val topic: LiveData<Topic> get() = _topic

    private val steps = Transformations.map(topic){
        it.steps
    }

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
        val list: MutableList<Step> = topic.value!!.steps?: mutableListOf<Step>()
        var step = Step(title=title, explanation = description)
        list.add(step)

        Log.d("Step", "adding")
        viewModelScope.launch {
                repository.addStep(topic.value!!.title, list)
                Log.d("Step", "added")
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