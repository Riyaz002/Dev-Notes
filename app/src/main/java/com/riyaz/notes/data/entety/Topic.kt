package com.riyaz.notes.data.entety

import androidx.room.*
import com.riyaz.notes.util.TypeConvertor

@Entity(tableName="topic_table")
data class Topic(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") val description: String,
    var notes: List<Note>,
    @ColumnInfo(name = "version_range") val versionRange: String?,
    var steps: List<Step>,
    //TODO: get rid of nullability
){

    constructor(title: String, description: String): this(0,title, description, listOf(), null, mutableListOf()){}
    constructor(title: String, description: String, steps: List<Step>): this(0, title, description, listOf(), null, steps){}

    override fun equals(other: Any?): Boolean {
        if(other?.javaClass?.isAssignableFrom(this.javaClass)!=true) return false
        other as Topic
        return other.id == this.id
    }
}
