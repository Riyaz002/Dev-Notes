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
import com.riyaz.notes.data.entety.Topic
import com.riyaz.notes.databinding.TopicListItemBinding
import com.riyaz.notes.ui.topicdetail.TopicDetailFragment

const val TITLE = "title"
const val DESCRIPTION = "description"

class TopicAdapter: ListAdapter<Topic, TopicAdapter.TopicViewHolder>(TopicDiffCallback()) {

    class TopicViewHolder(private val binding: TopicListItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Topic) {
            binding.topic = item
            binding.root.setOnClickListener {
                //Toast.makeText(binding.root.context, "Topic: ${item.title}", Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                //bundle.putString(TITLE,item.title)
                bundle.putInt("ID", item.id)

                val topicDetailFragment = TopicDetailFragment()
                topicDetailFragment.arguments = bundle

                val activity = binding.root.context as MainActivity
                activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_view, topicDetailFragment).addToBackStack("TopicDetailFragment").commit()
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
            fun from(parent: ViewGroup): TopicViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    TopicListItemBinding.inflate(layoutInflater, parent, false)
                return TopicViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        return TopicViewHolder.from(parent)
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
        return oldItem.description == newItem.description
                && oldItem.steps == newItem.steps
                && oldItem.versionRange == newItem.versionRange
                && oldItem.notes == newItem.notes
    }
}