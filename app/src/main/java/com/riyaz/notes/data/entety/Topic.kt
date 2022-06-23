package com.riyaz.notes.data.entety

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="topic_table")
data class Topic(
    @PrimaryKey var title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "notes") val notes: List<String?>?,
    @ColumnInfo(name = "version_range") val versionRange: String?,
    @ColumnInfo(name = "steps") val steps: List<Step>?
){
    constructor(title: String, description: String): this(title, description, null, null, null){}
    constructor(title: String, description: String, steps: List<Step>): this(title, description, null, null, steps){}
}
