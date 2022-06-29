package com.riyaz.notes.data.dao

import androidx.room.*
import com.riyaz.notes.data.entety.Step
import com.riyaz.notes.data.entety.Topic
import com.riyaz.notes.util.TypeConvertor
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTopic(topic: Topic)

    @Delete
    suspend fun deleteTopic(topic: Topic)

    @Update
    suspend fun updateTopic(topic: Topic)

    @Query("SELECT * FROM topic_table WHERE title==:title")
    suspend fun getTopic(title: String): Topic

    @Query("SELECT * FROM topic_table")
    fun getAllTopics(): Flow<List<Topic>>

    @Query("SELECT * FROM topic_table WHERE title LIKE :query")
    fun getSearchTopics(query: String): Flow<List<Topic>>

    @Query("UPDATE topic_table SET steps = :step WHERE title = :title")
    suspend fun addStep(title: String, step: MutableList<Step>)

//    @Query("SELECT steps FROM topic_table WHERE title==:title")
//    fun getSteps(title: String): Flow<List<Step>?>
}