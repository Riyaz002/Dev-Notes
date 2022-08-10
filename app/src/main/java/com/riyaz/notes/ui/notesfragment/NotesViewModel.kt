package com.riyaz.notes.ui.notesfragment

import androidx.lifecycle.*
import com.riyaz.notes.data.entety.Note
import com.riyaz.notes.data.entety.Topic
import com.riyaz.notes.repository.TopicRepository
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: TopicRepository, private val id: Int) : ViewModel() {
    private val _topic = repository.getTopic(id).asLiveData()
    val topic: LiveData<Topic> get() = _topic

    private val scope = viewModelScope
    
    fun addNote(content: String){
        scope.launch {
            val list = createNewNoteList(content)
            repository.addNote(id, list)
        }
    }

    private fun createNewNoteList(content: String): List<Note> {
        val list: MutableList<Note> = topic.value?.notes?.toMutableList()?: mutableListOf()
        val note = Note(content)
        list.add(note)
        return list
    }
}

class NotesViewModelProvider(private val repository: TopicRepository, val id: Int): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotesViewModel::class.java)){
            return NotesViewModel(repository ,id) as T
        }
        throw IllegalArgumentException("$modelClass cannot be assigned to NotesViewModel")
    }

}