package com.bangkit.snapcook.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(_binding?.root)

        initIntent()
        initUI()
        initActions()
        initProcess()
        initObservers()
    }

    abstract fun getViewBinding(): VB

    abstract fun initUI()

    abstract fun initProcess()

    abstract fun initObservers()

    protected open fun initIntent() {}

    protected open fun initActions() {}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}