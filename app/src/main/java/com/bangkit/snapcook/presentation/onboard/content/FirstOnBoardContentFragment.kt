package com.bangkit.snapcook.presentation.onboard.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentFirstOnBoardContentBinding

class FirstOnBoardContentFragment : BaseFragment<FragmentFirstOnBoardContentBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentFirstOnBoardContentBinding {
        return FragmentFirstOnBoardContentBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }

}