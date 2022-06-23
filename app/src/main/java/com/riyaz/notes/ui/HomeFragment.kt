package com.riyaz.notes.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.riyaz.notes.R
import com.riyaz.notes.data.database.TopicDatabase
import com.riyaz.notes.databinding.FragmentHomeBinding
import com.riyaz.notes.repository.TopicRepository
import com.riyaz.notes.ui.dialoguefragment.TopicDialogueFragment

class HomeFragment : Fragment(), TopicDialogueFragment.MyDialogueCallbackListener, SearchView.OnQueryTextListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var database: TopicDatabase
    private lateinit var repository: TopicRepository
    private lateinit var binding: FragmentHomeBinding
    private lateinit var topicAdapter: TopicAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
       val dialogFragment = TopicDialogueFragment()
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
        viewModel.persistNewTopic(topic, description)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_items, menu)
        val search = menu?.findItem(R.id.search_item)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        searchTopics(query)
        return true
    }
    private fun searchTopics(query: String?){
        val searchQuery = "%$query%"
        viewModel.searchTopics(searchQuery)?.observe(viewLifecycleOwner, Observer {
            it?.let {
                topicAdapter.submitList(it)
            }
        })
    }
}

