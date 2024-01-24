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
import com.riyaz.notes.ui.MainActivity
import com.riyaz.notes.R
import com.riyaz.notes.core.enums.Page
import com.riyaz.notes.core.router.RouteExecutionInterface
import com.riyaz.notes.data.dao.TopicDao
import com.riyaz.notes.data.database.TopicDatabase
import com.riyaz.notes.databinding.FragmentTopicDetailBinding
import com.riyaz.notes.repository.TopicRepository
import com.riyaz.notes.ui.dialoguefragment.MyDialogueCallbackListener
import com.riyaz.notes.ui.dialoguefragment.StepDialogueFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val TITLE = "title"
const val DESCRIPTION = "description"

@AndroidEntryPoint
class TopicDetailFragment : Fragment(), MyDialogueCallbackListener, DialogueOutsideTouchListeners {

    private lateinit var binding: FragmentTopicDetailBinding
    private lateinit var dialogue: AlertDialog.Builder
    @Inject lateinit var topicDao: TopicDao
    private lateinit var repository: TopicRepository
    private lateinit var adapter: StepsAdapter
    private lateinit var layoutManager: LinearLayoutManager

    companion object {
        fun newInstance() = TopicDetailFragment()
    }

    private lateinit var viewModel: TopicDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id: Int =  arguments?.getString("ID")?.toInt()!!
        repository = TopicRepository(topicDao)
        viewModel = ViewModelProvider(this, TopicDetailViewModelFactory(repository, id))[TopicDetailViewModel::class.java]
        viewModel.routeHandler = requireActivity() as RouteExecutionInterface
    }

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

        dialogue = AlertDialog.Builder(context)

        return binding.root
    }

    fun navigateToNotes(){
        val parameters = hashMapOf<String, String>().also { it.put("TopicID", viewModel.id.toString()) }
        viewModel.stateUpdater.openPage(Page.NOTES.id, parameters)
    }

    fun showTopicDeleteDialogue() {
            dialogue.setTitle("Delete Topic?")
                .setMessage("Once the topic is deleted, it cannot be retrieved")
                .setNegativeButton("NO"){ dialogInterface, i ->
                    dialogInterface.dismiss()
                }
                .setPositiveButton("YES"){ dialogueInterface, i ->
                    viewModel.deleteTopic()
                    navigateBack()
                }
                .create().show()
    }

    fun showAddStepDialogue(){
        val stepDialogueFragment = StepDialogueFragment()
        stepDialogueFragment.show(parentFragmentManager, null)
    }

    private fun navigateBack() {
        val activity = activity as MainActivity
        activity.supportFragmentManager.popBackStack()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
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
        viewModel.addStep(title, description)
    }

    override fun touchedOutside() {
        //isDialogueOpen=false
    }

    override fun cancel() {
        //isDialogueOpen = false
    }
}


//TODO: remove it if not useful
interface DialogueOutsideTouchListeners{
    fun touchedOutside()
}

