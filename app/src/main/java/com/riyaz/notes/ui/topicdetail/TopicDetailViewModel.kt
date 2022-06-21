package com.riyaz.notes.ui.topicdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TopicDetailViewModel : ViewModel() {
    // TODO: Implement the ViewModel
}

class TopicDetailViewModelFactory: ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TopicDetailViewModel::class.java)){
            return TopicDetailViewModel() as T
        } else{
            throw IllegalArgumentException("ViewModel class is no assignable")
        }
    }
}