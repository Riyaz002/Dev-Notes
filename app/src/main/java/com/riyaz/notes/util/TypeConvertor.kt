package com.riyaz.notes.util

import androidx.room.TypeConverter
import com.riyaz.notes.data.entety.Step

object TypeConvertor {
    @TypeConverter
    fun fromString(stringListString: String?): List<String?>? {
        return stringListString?.split(",")?.map { it } ?: return ArrayList<String>()
    }

    @TypeConverter
    fun toString(stringList: List<String?>?): String? {
        return stringList?.joinToString(separator = ",") ?: "No Notes"
    }

    @TypeConverter
    fun fromSteps(steps: List<Step>?): String?{
        return steps?.joinToString("%step%")
    }

    @TypeConverter
    fun toSteps(stepsString: String?): MutableList<Step>? {
        if (stepsString == null) return null
        val list = stepsString.split("%step%")
        val stepList = mutableListOf<Step>()
        list.forEach { stepInString ->
            val id = stepInString.substring(
                stepInString.indexOf("id=") + 3,
                stepInString.indexOf(", title=")
            )
            val title = stepInString.substring(
                stepInString.indexOf(", title=") + 8,
                stepInString.indexOf(", explanation=")
            )
            val explanation = stepInString.substring(
                stepInString.indexOf(", explanation=") + 14,
                stepInString.length - 1
            )
            if (id != "null") stepList.add(Step(id?.toInt(), title, explanation))
            else stepList.add(Step(null, title, explanation))
        }
        return stepList.toMutableList()
    }
}