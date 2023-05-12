package com.bangkit.snapcook.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentDetailRecipeBinding
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.setImageUrl
import org.koin.android.ext.android.inject

class DetailRecipeFragment : BaseFragment<FragmentDetailRecipeBinding>() {

    private val viewModel: DetailRecipeViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailRecipeBinding {
        return FragmentDetailRecipeBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            imgFood.setImageUrl("https://img.herstory.co.id/articles/archive_20210707/laksa-20210707-103155.jpg")
            btnBookmark.popClick {

            }
            btnBuyIngredient.popClick {

            }
            btnStartCooking.popClick {

            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }

}