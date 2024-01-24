package com.riyaz.notes.ui.homefragment

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.riyaz.notes.repository.FakeTopicRepository
import com.riyaz.notes.util.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HomeViewModelTest{

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp(){
        homeViewModel = HomeViewModel(FakeTopicRepository())
    }

    @Test
    fun testPersistNewTopic()= runBlocking(Dispatchers.Main) {
        homeViewModel.persistNewTopic("my title", "nothing")
        val result = homeViewModel.allTopics.getOrAwaitValue(2).any { it.title == "my title" }
        Assert.assertEquals(true, result)
    }

    @Test
    fun testSearchTopic() = runBlocking(Dispatchers.Main) {
        homeViewModel.persistNewTopic("title 1", "nothing")
        homeViewModel.persistNewTopic("title 2", "nothing")
        homeViewModel.persistNewTopic("title 3", "nothing")
        homeViewModel.persistNewTopic("title 4", "nothing")

        val result = homeViewModel.searchTopics("title 1").getOrAwaitValue(2).any{ it.title == "title 1" }
        Assert.assertEquals(true, result)
    }


}