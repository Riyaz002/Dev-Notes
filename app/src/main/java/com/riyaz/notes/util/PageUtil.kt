package com.riyaz.notes.util

import androidx.fragment.app.Fragment
import com.riyaz.notes.core.enums.Page
import com.riyaz.notes.core.exception.PageNotFoundException
import com.riyaz.notes.ui.homefragment.HomeFragment
import com.riyaz.notes.ui.notesfragment.NotesFragment
import com.riyaz.notes.ui.stepsdetail.StepDetailFragment
import com.riyaz.notes.ui.topicdetail.TopicDetailFragment

object PageUtil {

    fun getPage(id: String): Fragment {
        return when(id){
            Page.HP.id -> HomeFragment()
            Page.TDP.id -> TopicDetailFragment()
            Page.SDP.id -> StepDetailFragment()
            Page.NOTES.id -> NotesFragment()
            else -> {
                throw PageNotFoundException(id)
            }
        }
    }

}