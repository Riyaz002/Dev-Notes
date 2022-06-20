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
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.riyaz.notes.R

class TopicDialogueFragment: DialogFragment() {

    lateinit var listener: MyDialogueCallbackListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.dialogue_layout,null , false)
        val create = view.findViewById<MaterialButton>(R.id.dialog_create_button)
        val alertDialogBuilder = AlertDialog.Builder(parentFragment?.context)
            alertDialogBuilder.setView(view)
                .setTitle("New Topic")
                .setNegativeButton("CANCEL",getCancelOnClickListener())
                .setPositiveButton("CREATE", getCreateOnClickListener(view, parentFragment?.context))
        return alertDialogBuilder.create()
    }

    private fun getCreateOnClickListener(view: View, context: Context?): DialogInterface.OnClickListener? {
        val listener = DialogInterface.OnClickListener { dialogInterface, i ->
            listener = context as MyDialogueCallbackListener
            val topic = view.findViewById<TextView>(R.id.dialog_et_title).text.toString()
            val description = view.findViewById<TextView>(R.id.dialog_et_description).text.toString()
            listener.createTopic(topic, description)
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