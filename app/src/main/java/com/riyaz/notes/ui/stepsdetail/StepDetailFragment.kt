package com.riyaz.notes.ui.stepsdetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.riyaz.notes.R

class StepDetailFragment : Fragment() {

    companion object {
        fun newInstance() = StepDetailFragment()
    }

    private lateinit var viewModel: StepDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step_detail, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StepDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}