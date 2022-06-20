package com.riyaz.notes.data.entety

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Steps(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name="step_title") val title: String?,
    @ColumnInfo(name="sub_steps") val subSteps: String?
)
