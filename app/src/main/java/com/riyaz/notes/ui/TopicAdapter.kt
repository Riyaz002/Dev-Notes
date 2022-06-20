package com.riyaz.notes.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.riyaz.notes.data.entety.Topic
import com.riyaz.notes.databinding.TopicListItemBinding

class TopicAdapter: ListAdapter<Topic, TopicAdapter.TopicViewHolder>(TopicDiffCallback()) {

    class TopicViewHolder(private val binding: TopicListItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Topic) {
            binding.title.text = item.title
            binding.description.text = item.description
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

class TopicDiffCallback : DiffUtil.ItemCallback<Topic>(){
    override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
        return oldItem.description == newItem.description
                && oldItem.steps == newItem.steps
                && oldItem.versionRange == newItem.versionRange
                && oldItem.notes == newItem.notes
    }

}