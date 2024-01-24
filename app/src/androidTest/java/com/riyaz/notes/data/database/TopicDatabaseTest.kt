package com.riyaz.notes.data.database

import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.riyaz.ApplicationTest
import com.riyaz.notes.data.dao.TopicDao
import com.riyaz.notes.data.entety.Step
import com.riyaz.notes.data.entety.Topic
import com.riyaz.notes.ui.homefragment.HomeFragment
import com.riyaz.notes.ui.launchFragmentInHiltContainer
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named


@SmallTest
@HiltAndroidTest
class TopicDatabaseTest{

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var dataBase: TopicDatabase

    lateinit var dao: TopicDao

    @Before
    fun setup(){
        hiltRule.inject()
        dao = dataBase.topicDao()
    }

    @After
    fun tearDown(){
        dataBase.close()
    }

    @Test
    fun checkTopicInsertFunction() = runBlocking {
        val topic = Topic("my title", "some description", listOf(Step(title = "step1", explanation = "some explanation"), Step(title = "step2", explanation = "some explanation"), Step(title = "step3", explanation = "some explanation")))
        dao.addTopic(topic)
        val result = dao.getLastAdded()?.title == topic.title
        assertEquals(true, result)
    }

    @Test
    fun checkTopicDeletion() = runBlocking{
        val topic = Topic("my title", "some description", listOf(Step(title = "step1", explanation = "some explanation"), Step(title = "step2", explanation = "some explanation"), Step(title = "step3", explanation = "some explanation")))
        dao.addTopic(topic)
        var result = dao.getLastAdded()?.title == topic.title
        assertEquals(true, result)
        dao.deleteTopic(dao.getLastAdded()?:topic)
        result = dao.getLastAdded() == null
        assertEquals(true, result)
    }

    @Test
    fun checkTopicUpdate() = runBlocking{
        var topic = Topic("my title", "some description", listOf(Step(title = "step1", explanation = "some explanation"), Step(title = "step2", explanation = "some explanation"), Step(title = "step3", explanation = "some explanation")))
        dao.addTopic(topic)

        var result = dao.getLastAdded()?.title == topic.title
        assertEquals(true, result)

        topic = dao.getLastAdded()!!
        topic.title = "sasa"
        dao.updateTopic(topic)

        topic = dao.getLastAdded()!!
        result = topic.title == "sasa"
        assertEquals(true, result)
    }

    @Test
    fun checkAddStep() = runBlocking{
        var topic = Topic("my title", "some description", listOf(Step(title = "step1", explanation = "some explanation"), Step(title = "step2", explanation = "some explanation"), Step(title = "step3", explanation = "some explanation")))
        dao.addTopic(topic)
        topic = dao.getLastAdded()!!
        val newStep = Step(title = "new step", explanation = "new explanation")
        val newSteps = topic.steps.map { it -> it }.toMutableList()
        newSteps.add(newStep)
        dao.addStep(topic.id, newSteps)
        val newtopic = dao.getLastAdded()!!
        var result = topic.steps.size != newSteps.size
        assertEquals(true, result)
        result = newtopic.steps.size == newSteps.size
        assertEquals(true, result)
    }
}

