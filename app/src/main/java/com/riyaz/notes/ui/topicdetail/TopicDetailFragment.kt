package com.riyaz.notes.ui.topicdetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.riyaz.notes.R
import com.riyaz.notes.databinding.FragmentTopicDetailBinding
import com.riyaz.notes.databinding.TopicListItemBinding

class TopicDetailFragment : Fragment() {

    private lateinit var binding: FragmentTopicDetailBinding

    companion object {
        fun newInstance() = TopicDetailFragment()
    }

    private lateinit var viewModel: TopicDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = this.arguments
        val title: String = args?.get("title").toString()
        val description: String = args?.get("description").toString()
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_topic_detail, container, false)

        binding.detailTitle.text = title
        binding.detailDescription.text = description

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TopicDetailViewModel::class.java)
        // TODO: Use the ViewModel

    }
}

