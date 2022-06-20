package com.riyaz.notes.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.riyaz.notes.data.dao.TopicDao
import com.riyaz.notes.data.entety.Topic

@Database(entities = [Topic::class], version = 1, exportSchema = false)
abstract class TopicDatabase(val appContext: Context): RoomDatabase() {
    abstract fun topicDao(): TopicDao

    companion object{
        @Volatile var INSTANCE: TopicDatabase? = null

        fun getDatabase(context: Context): TopicDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room
                    .databaseBuilder(context.applicationContext, TopicDatabase::class.java, "topic")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}