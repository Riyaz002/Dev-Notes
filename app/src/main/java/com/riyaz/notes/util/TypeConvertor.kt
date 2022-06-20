package com.riyaz.notes.util

import androidx.room.TypeConverter

object TypeConvertor {
    @TypeConverter
    fun fromString(stringListString: String?): List<String?>? {
        return stringListString?.split(",")?.map { it } ?: return ArrayList<String>()
    }

    @TypeConverter
    fun toString(stringList: List<String?>?): String? {
        return stringList?.joinToString(separator = ",") ?: "No Notes"
    }
}