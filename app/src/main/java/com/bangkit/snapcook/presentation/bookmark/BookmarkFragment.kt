package com.bangkit.snapcook.presentation.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentBookmarkBinding
import com.bangkit.snapcook.utils.helper.extractData

class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentBookmarkBinding {
        return FragmentBookmarkBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.btnBtn.setOnClickListener{
            val input1 = "100 gr daging kurma tanpa biji"
            val input2 = "2 siung bawang, potong dadu"
            val input3 = "Ayam 3kg"
            val input4 = "12 lembar kulit lumpia"

            val result1 = input1.extractData()

            println("Input 1: $input1")
            println("Result 1-1: ${result1.first}")
            println("Result 1-2: ${result1.second}")

        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }

}