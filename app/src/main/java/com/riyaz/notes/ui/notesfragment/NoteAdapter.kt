package com.riyaz.notes.ui.notesfragment


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.riyaz.notes.R
import com.riyaz.notes.data.entety.Note


class NoteAdapter : ListAdapter<Note, NoteAdapter.ViewHolder>(NoteDiffCallback()){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val number = view.findViewById<TextView>(R.id.txt_note_id)
        val content = view.findViewById<TextView>(R.id.txt_note_content)

        companion object{
            fun from(parent: ViewGroup): NoteAdapter.ViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.note_item, parent ,false)
                return NoteAdapter.ViewHolder(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return NoteAdapter.ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        "${(position+1)}.".also { holder.number.text = it }
        holder.content.text = getItem(position).content
    }
}

class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.content == newItem.content
    }

}