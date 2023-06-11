package com.bangkit.snapcook.presentation.onboard.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentSecondOnboardContentBinding

class SecondOnboardContentFragment : BaseFragment<FragmentSecondOnboardContentBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentSecondOnboardContentBinding {
        return FragmentSecondOnboardContentBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }

}