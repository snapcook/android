package com.bangkit.snapcook.presentation

import com.bangkit.snapcook.base.BaseActivity
import com.bangkit.snapcook.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
    override fun initUI() {}

    override fun initProcess() {}

    override fun initObservers() {}
}