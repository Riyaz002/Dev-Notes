package com.riyaz.notes.data.dao

import androidx.room.*
import com.riyaz.notes.data.entety.Topic
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTopic(topic: Topic)

    //@Query("SELECT * FROM topic_table WHERE title==:title")
    //suspend fun deleteTopic(title: String)

    @Query("SELECT * FROM topic_table")
    fun getAllTopics(): Flow<List<Topic>>
}