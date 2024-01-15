package com.riyaz.notes.core.model

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.riyaz.notes.core.constant.Page
import java.util.HashMap

data class Page(
    val pageId: String,
    val fragment: Fragment,
    val parameters: HashMap<String, String> = hashMapOf()
) {
    init {
        if(parameters.isNotEmpty()){
            val bundle = Bundle()
            parameters.forEach {
                bundle.putString(it.key, it.value)
            }
            fragment.arguments = bundle
        }
    }
}