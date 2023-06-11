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
import com.bangkit.snapcook.utils.extension.*
import org.koin.android.ext.android.inject

class RecommendedFragment : BaseFragment<FragmentRecommendedBinding>() {

    private val viewModel: RecommendedViewModel by inject()
    var result = emptyArray<String>()

    private val recipeAdapter: RecommendedRecipeAdapter by lazy {
        RecommendedRecipeAdapter(
            onClick = { navigateToDetail(it) },
            onBookmarkClick = { recipe ->
                if (recipe.isBookmarked) {
                    viewModel.removeBookmark(recipe.id)
                    binding.root.showSnackBar("Resep dihapus dari Bookmark.")
                } else {
                    binding.root.showSnackBar("Resep ditambahkan ke Bookmark.")
                    viewModel.addBookmark(recipe.id)
                }
            }
        )
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
            toolBar.setPopBackEnabled()
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
                binding.rvRecommendedRecipe.slowShow()
                binding.emptyListLayout.root.gone()
                recipeAdapter.setData(it.data)
            },
            empty = {
                showLoading(false)
                binding.emptyListLayout.root.slowShow()
                binding.rvRecommendedRecipe.gone()
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