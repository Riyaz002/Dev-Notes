package com.riyaz.notes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.riyaz.notes.core.router.RouteExecutionInterface
import com.riyaz.notes.data.dao.TopicDao
import com.riyaz.notes.data.database.TopicDatabase
import com.riyaz.notes.ui.homefragment.HomeFragment
import java.util.HashMap

class MainActivity : AppCompatActivity(), RouteExecutionInterface {

    lateinit var topicDao: TopicDao
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        topicDao = TopicDatabase.getDatabase(applicationContext).topicDao()
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.currentPage.observe(this){
            supportFragmentManager.beginTransaction().replace(R.id.fragment_view, it.fragment, it.pageId).addToBackStack(it.pageId).commit()
        }
    }

    override fun executePage(pageId: String, parameters: HashMap<String, String>) {
        mainViewModel.routeHandler.executePage(
            pageId,
            parameters
        )
    }

    override fun executeFunction() {
        mainViewModel.routeHandler.executeFunction()
    }
}

