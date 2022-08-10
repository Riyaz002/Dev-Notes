package com.riyaz.notes.ui.dialoguefragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.riyaz.notes.MainActivity
import com.riyaz.notes.R
import com.riyaz.notes.databinding.DialogueLayoutBinding
import com.riyaz.notes.ui.topicdetail.DialogueOutsideTouchListeners

class TopicDialogueFragment : DialogFragment() {

    private var listener: MyDialogueCallbackListener? = null
    lateinit var dialogueOutsideTouchListeners: DialogueOutsideTouchListeners
    private lateinit var binding: DialogueLayoutBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialogue_layout, null, false)
        binding.apply {
            dialogEtTitle.editText?.doOnTextChanged { text, start, before, count ->
                if (text.toString().isEmpty()) dialogEtTitle.error = "this field Shouldn't be empty"
                else dialogEtTitle.error=null
            }

            dialogEtDescription.editText?.doOnTextChanged { text, start, before, count ->
                if (text.toString().isEmpty()) dialogEtDescription.error = "this field shouldn't be empty"
                else dialogEtDescription.error = null
            }
        }

        val alertDialogBuilder = AlertDialog.Builder(activity)
            alertDialogBuilder.setView(binding.root)
                .setTitle("New Topic")
                .setNegativeButton("CANCEL", cancelOnClickListener())
                .setPositiveButton("CREATE", createOnClickListener())
        dialogueOutsideTouchListeners = (activity as MainActivity).supportFragmentManager.findFragmentById(R.id.fragment_view) as DialogueOutsideTouchListeners
        return alertDialogBuilder.create()
    }

    private fun createOnClickListener(): DialogInterface.OnClickListener? {
        val listener = DialogInterface.OnClickListener { dialogInterface, i ->
            listener = (activity as MainActivity).supportFragmentManager.findFragmentById(R.id.fragment_view) as MyDialogueCallbackListener
            val topic = binding.dialogEtTitle.editText?.text.toString()
            val description = binding.dialogEtDescription.editText?.text.toString()
            listener?.create(topic, description)
            listener = null;
        }
        return listener
    }

    private fun cancelOnClickListener(): DialogInterface.OnClickListener? {
        val listener = DialogInterface.OnClickListener { dialogInterface, i ->
            listener = (activity as MainActivity).supportFragmentManager.findFragmentById(R.id.fragment_view) as MyDialogueCallbackListener
            listener!!.cancel();
        }
        return listener
    }

    override fun onDestroy() {
        dialogueOutsideTouchListeners.touchedOutside()
        super.onDestroy()
    }
}