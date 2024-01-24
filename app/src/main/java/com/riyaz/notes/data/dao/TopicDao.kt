package com.riyaz.notes.data.dao

import androidx.room.*
import com.riyaz.notes.data.entety.Note
import com.riyaz.notes.data.entety.Step
import com.riyaz.notes.data.entety.Topic
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {

    //TODO: since there can be two or more topics of same type, update WHERE clause to check for 'description' as well

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTopic(topic: Topic)

    @Delete
    suspend fun deleteTopic(topic: Topic)

    @Update
    suspend fun updateTopic(topic: Topic)

    @Query("SELECT * FROM topic_table WHERE id==:id")
    fun getTopic(id: Int): Flow<Topic>

    @Query("SELECT * FROM topic_table ORDER BY id DESC LIMIT 1")
    fun getLastAdded(): Topic?


    @Query("SELECT * FROM topic_table")
    fun getAllTopics(): Flow<List<Topic>>

    @Query("SELECT * FROM topic_table WHERE title LIKE :query")
    fun getSearchTopics(query: String): Flow<List<Topic>>

    @Query("UPDATE topic_table SET steps = :step WHERE id = :id")
    suspend fun addStep(id: Int, step: List<Step>)

    @Query("UPDATE topic_table SET notes = :notes WHERE id = :id")
    suspend fun addNote(id: Int, notes: List<Note>)

//    @Query("SELECT steps FROM topic_table WHERE title==:title")
//    fun getSteps(title: String): LiveData<List<Step>>
}