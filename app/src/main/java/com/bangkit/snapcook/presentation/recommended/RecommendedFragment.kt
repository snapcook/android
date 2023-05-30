package com.bangkit.snapcook.presentation.recommended

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentRecommendedBinding
import com.bangkit.snapcook.presentation.home.HomeFragmentDirections
import com.bangkit.snapcook.presentation.recommended.adapter.RecommendedRecipeAdapter
import com.bangkit.snapcook.utils.extension.observeResponse
import org.koin.android.ext.android.inject

class RecommendedFragment : BaseFragment<FragmentRecommendedBinding>() {

    private val viewModel: RecommendedViewModel by inject()
    var result = emptyArray<String>()

    private val recipeAdapter: RecommendedRecipeAdapter by lazy {
        RecommendedRecipeAdapter {
            navigateToDetail(it)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentRecommendedBinding {
        return FragmentRecommendedBinding.inflate(inflater, container, false)
    }

    override fun initIntent() {
        val safeArgs = arguments?.let { RecommendedFragmentArgs.fromBundle(it) }
        result = safeArgs?.result ?: emptyArray()
    }

    override fun initUI() {
        binding.apply {
            rvRecommendedRecipe.apply {
                adapter = recipeAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun initProcess() {
        viewModel.getRecipe(result.toList())
    }

    override fun initObservers() {
        viewModel.recipeResult.observeResponse(
            viewLifecycleOwner,
            loading = {
                showLoadingDialog()
            },
            success = {
                hideLoadingDialog()
                recipeAdapter.setData(it.data)
            },
            empty = {
                hideLoadingDialog()
            },
            error = {
                hideLoadingDialog()
            }
        )
    }

    private fun navigateToDetail(slug: String) {
        findNavController().navigate(
            RecommendedFragmentDirections.actionRecommendedFragmentToDetailRecipeFragment(
                slug
            )
        )
    }


}