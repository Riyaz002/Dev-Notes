package com.riyaz.notes.ui.topicdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.riyaz.notes.R
import com.riyaz.notes.data.entety.Step

class StepsAdapter: RecyclerView.Adapter<StepsAdapter.stepsViewholder>() {
    var list = listOf<Step>()

    class stepsViewholder(view: View): RecyclerView.ViewHolder(view){


        val stepNumber = view.findViewById<TextView>(R.id.step_number)
        val stepTitle = view.findViewById<TextView>(R.id.step_title)
        val stepContent = view.findViewById<TextView>(R.id.step_content)

        fun bind(list: List<Step>, position: Int) {
            stepNumber.text = (position+1).toString()+"."
            stepTitle.text = list[position].title
            stepContent.text = list[position].explanation
        }

        companion object{
            fun inflate(parent: ViewGroup): stepsViewholder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.steps_item, parent, false)
                return stepsViewholder(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): stepsViewholder {
        return stepsViewholder.inflate(parent)
    }

    override fun onBindViewHolder(holder: stepsViewholder, position: Int) {
        holder.bind(list, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}