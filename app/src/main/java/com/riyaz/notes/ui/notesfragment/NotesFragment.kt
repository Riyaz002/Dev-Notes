package com.riyaz.notes.ui.notesfragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.marginBottom
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.riyaz.notes.R
import com.riyaz.notes.data.database.TopicDatabase
import com.riyaz.notes.repository.TopicRepository

class NotesFragment : Fragment() {

    private lateinit var viewModel: NotesViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoteAdapter
    private lateinit var btnAddNote: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes, container, false)
        btnAddNote = view.findViewById(R.id.btn_add_note)
        btnAddNote.setOnClickListener {
            openDialogue()
        }
        setUpNotesRecyclerView(view)
        return view
    }

    private fun openDialogue() {
        val input = EditText(requireContext())

        input.inputType = InputType.TYPE_CLASS_TEXT
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add Note")
            .setView(input)
            .setPositiveButton("ADD") { dialogInterface, i ->
                if(input.text.isEmpty()){
                    input.error = "field must not be empty"
                } else{
                   viewModel.addNote(input.text.toString())
                }
            }.setNegativeButton("CANCEL"){ dialogInterface, i ->
                dialogInterface.cancel()
            }.create()

        dialog.show()
    }

    private fun setUpNotesRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view_notes)
        adapter = NoteAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val database = TopicDatabase.getDatabase(requireContext())
        val repository = TopicRepository(database.topicDao())
        val topicId = arguments?.getString("TopicID")?.toInt()!!
        viewModel = ViewModelProvider(this, NotesViewModelProvider(repository, topicId))[NotesViewModel::class.java]
        observeNoteList()
    }

    private fun observeNoteList() {
        viewModel.topic.observe(viewLifecycleOwner, Observer { topic ->
            topic?.let {
                adapter.submitList(it.notes)
            }
        })
    }
}