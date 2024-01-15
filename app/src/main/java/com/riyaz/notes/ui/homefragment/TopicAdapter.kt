package com.riyaz.notes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.riyaz.notes.MainActivity
import com.riyaz.notes.R
import com.riyaz.notes.core.constant.Page
import com.riyaz.notes.core.router.StateUpdater
import com.riyaz.notes.data.entety.Topic
import com.riyaz.notes.databinding.TopicListItemBinding
import com.riyaz.notes.ui.topicdetail.TopicDetailFragment

const val TITLE = "title"
const val DESCRIPTION = "description"

class TopicAdapter(val stateUpdater: StateUpdater) : ListAdapter<Topic, TopicAdapter.TopicViewHolder>(TopicDiffCallback()) {

    class TopicViewHolder(private val binding: TopicListItemBinding, val stateUpdater: StateUpdater): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Topic) {
            binding.topic = item
            binding.root.setOnClickListener {
                val parameters = hashMapOf<String, String>().also { it.put("ID", item.id.toString()) }
                stateUpdater.openPage(Page.TDP.id, parameters)
                val activity = binding.root.context as MainActivity
                closeKeyboard(activity)
            }
        }

        private fun closeKeyboard(activity: MainActivity) {
            // this will give us the view
            // which is currently focus
            // in this layout
            val view = activity.currentFocus

            // if nothing is currently
            // focus then this will protect
            // the app from crash
            if (view != null) {

                // now assign the system
                // service to InputMethodManager
                val manager = getSystemService(activity, InputMethodManager::class.java) as InputMethodManager
                manager.hideSoftInputFromWindow(view.windowToken, 0);
            }
        }

        companion object{
            fun from(parent: ViewGroup, stateUpdater: StateUpdater): TopicViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    TopicListItemBinding.inflate(layoutInflater, parent, false)
                return TopicViewHolder(binding, stateUpdater)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        return TopicViewHolder.from(parent, stateUpdater)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class TopicDiffCallback : DiffUtil.ItemCallback<Topic>() {
    override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
        return oldItem.title == newItem.title && oldItem.description == newItem.description
    }

    override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
        return (oldItem.description == newItem.description
                && (oldItem.steps == newItem.steps)
                && (oldItem.versionRange == newItem.versionRange)
                && (oldItem.notes == newItem.notes))
    }
}