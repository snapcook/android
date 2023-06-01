package com.bangkit.snapcook.presentation.recommended

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentRecommendedBinding
import com.bangkit.snapcook.presentation.recommended.adapter.DetectedIngredientAdapter
import com.bangkit.snapcook.presentation.recommended.adapter.RecommendedRecipeAdapter
import com.bangkit.snapcook.utils.extension.gone
import com.bangkit.snapcook.utils.extension.observeResponse
import com.bangkit.snapcook.utils.extension.show
import com.bangkit.snapcook.utils.extension.slowShow
import org.koin.android.ext.android.inject

class RecommendedFragment : BaseFragment<FragmentRecommendedBinding>() {

    private val viewModel: RecommendedViewModel by inject()
    var result = emptyArray<String>()

    private val recipeAdapter: RecommendedRecipeAdapter by lazy {
        RecommendedRecipeAdapter {
            navigateToDetail(it)
        }
    }

    private val ingredientAdapter: DetectedIngredientAdapter by lazy {
        DetectedIngredientAdapter()
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
        ingredientAdapter.setData(result.toList())
        binding.apply {
            rvRecommendedRecipe.apply {
                adapter = recipeAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            rvDetectedIngredient.apply {
                adapter = ingredientAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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
                showLoading(true)
            },
            success = {
                showLoading(false)
                recipeAdapter.setData(it.data)
            },
            empty = {
                showLoading(false)
            },
            error = {
                showLoading(false)
            }
        )
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmeringLoadingDetection.startShimmer()
                shimmeringLoadingDetection.showShimmer(true)
                shimmeringLoadingDetection.show()
                layoutRecommended.gone()
            } else {
                shimmeringLoadingDetection.stopShimmer()
                shimmeringLoadingDetection.showShimmer(false)
                shimmeringLoadingDetection.gone()
                layoutRecommended.slowShow()
            }
        }
    }

    private fun navigateToDetail(slug: String) {
        findNavController().navigate(
            RecommendedFragmentDirections.actionRecommendedFragmentToDetailRecipeFragment(
                slug
            )
        )
    }


}