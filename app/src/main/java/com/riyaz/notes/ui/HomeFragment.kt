package com.riyaz.notes.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.riyaz.notes.R
import com.riyaz.notes.data.database.TopicDatabase
import com.riyaz.notes.data.entety.Topic
import com.riyaz.notes.databinding.FragmentHomeBinding
import com.riyaz.notes.repository.TopicRepository
import com.riyaz.notes.ui.dialoguefragment.TopicDialogueFragment

class HomeFragment : Fragment(), TopicDialogueFragment.MyDialogueCallbackListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var database: TopicDatabase
    private lateinit var repository: TopicRepository
    private lateinit var binding: FragmentHomeBinding
    private lateinit var topicAdapter: TopicAdapter
    private lateinit var layoutManager: LinearLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.fab.setOnClickListener {
            showDialogue()
        }

        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        topicAdapter = TopicAdapter()

        binding.recyclerView.adapter = topicAdapter
        binding.recyclerView.layoutManager = layoutManager

        observeTopics()

        return binding.root
    }

    private fun showDialogue() {
        TODO("Not yet implemented")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        database = TopicDatabase.getDatabase(requireContext())
        repository = TopicRepository(database.topicDao())

        viewModel = ViewModelProvider(this, HomeFragmentViewModelFactory(repository)).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun observeTopics() {
        viewModel.allTopics.observe(viewLifecycleOwner, Observer {
            it?.let {
                topicAdapter.submitList(it)
            }
        })
    }

    override fun createTopic(topic: String, description: String) {
        viewModel.persistNewTopic(topic, description)
    }
}

