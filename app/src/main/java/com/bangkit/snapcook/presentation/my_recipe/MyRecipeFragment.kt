package com.bangkit.snapcook.presentation.my_recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.databinding.FragmentMyRecipeBinding
import com.bangkit.snapcook.presentation.recommended.RecommendedFragmentDirections
import com.bangkit.snapcook.utils.extension.*
import org.koin.android.ext.android.inject

class MyRecipeFragment : BaseFragment<FragmentMyRecipeBinding>() {

    private val viewModel: MyRecipeViewModel by inject()


    private val recipeAdapter: MyRecipeAdapter by lazy {
        MyRecipeAdapter {
            navigateToEditRecipe(it)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentMyRecipeBinding {
        return FragmentMyRecipeBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            toolBar.title = "My Recipe"
            toolBar.setPopBackEnabled()

            rvRecipe.apply {
                adapter = recipeAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
            }

            btnAddRecipe.popClick {
                findNavController().navigate(
                    MyRecipeFragmentDirections.actionMyRecipeFragmentToAddRecipeFragment(
                        ""
                    )
                )
            }

        }
    }

    override fun initProcess() {
        viewModel.getMyRecipe()
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

    private fun navigateToEditRecipe(recipe: Recipe) {
        findNavController().navigate(
            MyRecipeFragmentDirections.actionMyRecipeFragmentToAddRecipeFragment(
                recipe.slug
            )
        )
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmeringLoadingDetection.startShimmer()
                shimmeringLoadingDetection.showShimmer(true)
                shimmeringLoadingDetection.show()
                rvRecipe.gone()
            } else {
                shimmeringLoadingDetection.stopShimmer()
                shimmeringLoadingDetection.showShimmer(false)
                shimmeringLoadingDetection.gone()
                rvRecipe.slowShow()
            }
        }
    }

}