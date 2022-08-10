package com.riyaz.notes.data.entety

import androidx.room.*
import com.riyaz.notes.util.TypeConvertor

@Entity(tableName="topic_table")
data class Topic(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    val notes: List<Note>,
    @ColumnInfo(name = "version_range") val versionRange: String?,
    val steps: List<Step>,
    //TODO: get rid of nullability
){

    constructor(title: String, description: String): this(0,title, description, listOf(), null, mutableListOf()){}
    constructor(title: String, description: String, steps: List<Step>): this(0, title, description, listOf(), null, steps){}
}
