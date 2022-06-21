package com.riyaz.notes.ui.dialoguefragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.ClipDescription
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.riyaz.notes.R
import com.riyaz.notes.databinding.DialogueLayoutBinding
import com.riyaz.notes.ui.HomeFragment

class TopicDialogueFragment(var homeFrag: HomeFragment?): DialogFragment() {

    private var listener: MyDialogueCallbackListener? = null
    private lateinit var binding: DialogueLayoutBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DataBindingUtil.inflate<DialogueLayoutBinding>(layoutInflater, R.layout.dialogue_layout, null, false)

        val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setView(binding.root)
                .setTitle("New Topic")
                .setNegativeButton("CANCEL",getCancelOnClickListener())
                .setPositiveButton("CREATE", getCreateOnClickListener(binding.root))
        return alertDialogBuilder.create()
    }

    private fun getCreateOnClickListener(view: View): DialogInterface.OnClickListener? {
        val listener = DialogInterface.OnClickListener { dialogInterface, i ->
            listener = homeFrag as MyDialogueCallbackListener
            val topic = binding.dialogEtTitle.editText?.text.toString()
            val description = binding.dialogEtDescription.editText?.text.toString()
            listener?.createTopic(topic, description)
            listener = null;
            homeFrag = null;
        }
        return listener
    }

    private fun getCancelOnClickListener(): DialogInterface.OnClickListener? {
        val listener = DialogInterface.OnClickListener { dialogInterface, i ->
            dismiss()
        }
        return listener
    }

    interface MyDialogueCallbackListener{
        fun createTopic(topic: String, description: String);
    }
}