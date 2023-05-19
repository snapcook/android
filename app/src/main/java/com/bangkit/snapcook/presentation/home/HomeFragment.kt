package com.bangkit.snapcook.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentHomeBinding
import com.bangkit.snapcook.utils.extension.popClick
import org.koin.android.ext.android.inject


class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
//            fabCamera.popClick {
//                findNavController().navigate(R.id.action_homeFragment_to_detectIngredientFragment2)
//            }
            rvPopularRecipe.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                isNestedScrollingEnabled = true
            }

            chipGroupCategory.apply {
                isNestedScrollingEnabled = true
            }

            rvRecommendedRecipe.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                isNestedScrollingEnabled = true
            }

            rvUserTaste.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                isNestedScrollingEnabled = true
            }
        }

    }

    override fun initProcess() {

    }

    override fun initObservers() {

    }

}