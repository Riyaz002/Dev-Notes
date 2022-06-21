package com.riyaz.notes.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.riyaz.notes.R
import com.riyaz.notes.data.database.TopicDatabase
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

        return binding.root
    }

    private fun showDialogue() {
       val dialogFragment = TopicDialogueFragment(this)
        dialogFragment.show(parentFragmentManager,"Topic Dialogue")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        database = TopicDatabase.getDatabase(requireContext())
        repository = TopicRepository(database.topicDao())
        viewModel = ViewModelProvider(this, HomeFragmentViewModelFactory(repository)).get(HomeViewModel::class.java)
        observeTopics()
    }

    private fun observeTopics() {
        viewModel.allTopics.observe(viewLifecycleOwner, Observer {
            it?.let {
                topicAdapter.submitList(it)
            }
        })
    }

    override fun createTopic(topic: String, description: String) {
        //Toast.makeText(requireContext(), "Selected Topic: $topic", Toast.LENGTH_SHORT).show()
        viewModel.persistNewTopic(topic, description)
    }
}

