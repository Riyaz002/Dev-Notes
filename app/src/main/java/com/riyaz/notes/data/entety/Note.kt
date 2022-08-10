package com.riyaz.notes.data.entety

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val content: String
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}