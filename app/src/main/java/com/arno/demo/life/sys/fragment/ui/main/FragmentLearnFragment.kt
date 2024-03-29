package com.arno.demo.life.sys.fragment.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.arno.demo.life.R
import com.arno.demo.life.base.BaseLifeFragment

class FragmentLearnFragment : BaseLifeFragment() {

    companion object {
        fun newInstance() = FragmentLearnFragment()
    }

    private lateinit var viewModel: MainViewModel
    override val TAG: String
        get() = "FragmentLearnFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

}