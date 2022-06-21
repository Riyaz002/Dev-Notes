package com.riyaz.notes.ui.topicdetail

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.riyaz.notes.MainActivity
import com.riyaz.notes.R
import com.riyaz.notes.data.dao.TopicDao
import com.riyaz.notes.data.database.TopicDatabase
import com.riyaz.notes.databinding.FragmentTopicDetailBinding
import com.riyaz.notes.databinding.TopicListItemBinding
import com.riyaz.notes.repository.TopicRepository
import com.riyaz.notes.ui.HomeFragment

const val TITLE = "title"
const val DESCRIPTION = "description"

class TopicDetailFragment : Fragment() {

    private lateinit var binding: FragmentTopicDetailBinding
    private lateinit var dialogue: AlertDialog.Builder
    private lateinit var topicDao: TopicDao
    private lateinit var repository: TopicRepository
    private lateinit var title: String

    companion object {
        fun newInstance() = TopicDetailFragment()
    }

    private lateinit var viewModel: TopicDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Toast.makeText(this.context, "${viewModel.topic.value}", Toast.LENGTH_SHORT).show()
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_topic_detail, container, false)
        binding.fragment = this

        dialogue = AlertDialog.Builder(context)


        return binding.root
    }

    fun showDialogue() {
        dialogue.setTitle("Delete Topic?")
            .setMessage("Once the topic is deleted, you cannot undo it")
            .setNegativeButton("NO"){ dialogInterface, i ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("YES"){ dialogueInterface, i ->
                viewModel.deleteTopic()
                navigateBack()
            }.create().show()
    }

    private fun navigateBack() {

        val activity = activity as MainActivity
        activity.supportFragmentManager.popBackStack()
//        activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_view, HomeFragment()).commit()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        val args = this.arguments
        title = args?.get(TITLE).toString()
        //Toast.makeText(this.context, "${title}", Toast.LENGTH_SHORT).show()

        topicDao = TopicDatabase.getDatabase(requireContext()).topicDao()
        repository = TopicRepository(topicDao)


        viewModel = ViewModelProvider(this, TopicDetailViewModelFactory(repository, title))[TopicDetailViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }
}

