package com.riyaz.notes.data.database

import android.content.Context
import androidx.room.*
import com.riyaz.notes.data.dao.TopicDao
import com.riyaz.notes.data.entety.Note
import com.riyaz.notes.data.entety.Step
import com.riyaz.notes.data.entety.Topic
import com.riyaz.notes.util.TypeConvertor

@Database(entities = [Topic::class, Step::class, Note::class], version = 1, exportSchema = false)
@TypeConverters(TypeConvertor::class)
abstract class TopicDatabase: RoomDatabase() {
    abstract fun topicDao(): TopicDao

}