package com.riyaz.notes.ui.topicdetail

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.riyaz.notes.MainActivity
import com.riyaz.notes.R
import com.riyaz.notes.data.dao.TopicDao
import com.riyaz.notes.data.database.TopicDatabase
import com.riyaz.notes.databinding.FragmentTopicDetailBinding
import com.riyaz.notes.repository.TopicRepository
import com.riyaz.notes.ui.dialoguefragment.MyDialogueCallbackListener
import com.riyaz.notes.ui.dialoguefragment.StepDialogueFragment
import com.riyaz.notes.ui.notesfragment.NotesFragment

const val TITLE = "title"
const val DESCRIPTION = "description"

class TopicDetailFragment : Fragment(), MyDialogueCallbackListener, DialogueOutsideTouchListeners {

    private lateinit var binding: FragmentTopicDetailBinding
    private lateinit var dialogue: AlertDialog.Builder
    private lateinit var topicDao: TopicDao
    private lateinit var repository: TopicRepository
    private lateinit var title: String
    private lateinit var adapter: StepsAdapter
    private lateinit var layoutManager: LinearLayoutManager

    var isDialogueOpen = false

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

        adapter = StepsAdapter()
        layoutManager = LinearLayoutManager(context)
        binding.stepsRecyclerView.layoutManager = layoutManager
        binding.stepsRecyclerView.adapter = adapter
        binding.stepsRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

//        viewModel.getSteps().observe(viewLifecycleOwner, Observer {
//            it?.let { steps->
//                adapter.list = steps
//            }
//        })
        dialogue = AlertDialog.Builder(context)

        return binding.root
    }

    fun navigateToNotes(){
        val bundle = Bundle()
        bundle.putInt("TopicID", viewModel.id)
        val notesFragment = NotesFragment.newInstance()
        notesFragment.arguments = bundle

        val activity = activity as MainActivity
        activity.supportFragmentManager.beginTransaction().addToBackStack("Note Fragment").replace(R.id.fragment_view, notesFragment).commit()
    }

    fun showTopicDeleteDialogue() {
        if (!isDialogueOpen){
            dialogue.setTitle("Delete Topic?")
                .setMessage("Once the topic is deleted, it cannot be retrieved")
                .setNegativeButton("NO"){ dialogInterface, i ->
                    dialogInterface.dismiss()
                    isDialogueOpen=false
                }
                .setPositiveButton("YES"){ dialogueInterface, i ->
                    viewModel.deleteTopic()
                    isDialogueOpen=false
                    navigateBack()
                }
                .create().show()
            isDialogueOpen=true
        }
    }

    fun showAddStepDialogue(){
        if(!isDialogueOpen){
            val stepDialogueFragment = StepDialogueFragment()
            stepDialogueFragment.show(parentFragmentManager, null)
            isDialogueOpen=true
        }
    }

    private fun navigateBack() {
        val activity = activity as MainActivity
        activity.supportFragmentManager.popBackStack()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        val args = this.arguments
        //title = args?.get("ID").toString()
        val id: Int =  args?.get("ID") as Int

        topicDao = TopicDatabase.getDatabase(requireContext()).topicDao()
        repository = TopicRepository(topicDao)

        viewModel = ViewModelProvider(this, TopicDetailViewModelFactory(repository, id))[TopicDetailViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.topic.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.steps?.let { steps->
                    adapter.list = steps
                }
            }
        })

    }

    override fun create(title: String, description: String) {
        isDialogueOpen = false
        viewModel.addStep(title, description)
    }

    override fun touchedOutside() {
        isDialogueOpen=false
    }

    override fun cancel() {
        isDialogueOpen = false
    }
}


//TODO: remove it if not useful
interface DialogueOutsideTouchListeners{
    fun touchedOutside()
}

