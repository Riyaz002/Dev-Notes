package com.riyaz.notes.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.riyaz.notes.R
import com.riyaz.notes.data.database.TopicDatabase
import com.riyaz.notes.repository.TopicRepository

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var database: TopicDatabase
    private lateinit var repository: TopicRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        database = TopicDatabase.getDatabase(requireContext())
        repository = TopicRepository(database.topicDao())

        viewModel = ViewModelProvider(this, HomeFragmentViewModelFactory(repository)).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}