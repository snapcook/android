package com.bangkit.snapcook.presentation.cooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentCookingBinding
import com.bangkit.snapcook.presentation.cooking.adapter.CookingPagerAdapter
import com.bangkit.snapcook.presentation.cooking.adapter.CookingStepAdapter
import com.bangkit.snapcook.utils.constant.GroceryConstants.END_COOKING_STEP
import com.bangkit.snapcook.utils.constant.GroceryConstants.START_COOKING_STEP
import com.bangkit.snapcook.utils.extension.popClick

class CookingFragment : BaseFragment<FragmentCookingBinding>() {

    private var ingredients: List<String> = listOf()
    private var spices: List<String> = listOf()
    private var steps = mutableListOf<String>()

    private val cookingStepAdapter by lazy {
        CookingStepAdapter{
            binding.viewPager2.currentItem = it
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentCookingBinding {
        return FragmentCookingBinding.inflate(inflater, container, false)
    }

    override fun initIntent() {
        arguments?.let {
            ingredients = CookingFragmentArgs.fromBundle(it).ingredients.toList()
            spices = CookingFragmentArgs.fromBundle(it).spices.toList()
            steps = CookingFragmentArgs.fromBundle(it).steps.toMutableList()
            steps.add(0, START_COOKING_STEP)
            steps.add(steps.size, END_COOKING_STEP)
        }
    }

    override fun initUI() {
        binding.apply {
            viewPager2.adapter = CookingPagerAdapter(requireActivity(), steps, ingredients, spices)
            viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    cookingStepAdapter.onPageChanged(position)
                }
            })

            btnClose.popClick {
                findNavController().popBackStack()
            }

            rvStepCooking.apply {
                adapter = cookingStepAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }

            cookingStepAdapter.setData(steps)
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }

}