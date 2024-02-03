package com.arno.demo.life.fragment.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseLifeFragment : Fragment() {
    abstract val TAG: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w(TAG, "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.w(TAG, "onCreateView: ")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.w(TAG, "onViewCreated: ")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.w(TAG, "onViewStateRestored: ")
    }

    override fun onStart() {
        super.onStart()
        Log.w(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.w(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.w(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.w(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w(TAG, "onDestroy: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.w(TAG, "onDestroyView: ")
    }
}