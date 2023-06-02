package com.bangkit.snapcook.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.databinding.FragmentHomeBinding
import com.bangkit.snapcook.presentation.home.adapter.CategoryAdapter
import com.bangkit.snapcook.presentation.home.adapter.ListRecipeAdapter
import com.bangkit.snapcook.presentation.modal.utensils.UtensilsAdapter
import com.bangkit.snapcook.utils.extension.observeResponse
import com.bangkit.snapcook.utils.extension.showSnackBar
import org.koin.android.ext.android.inject


class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by inject()

    private var selectedCategory = "Makanan"

    private val listRecipeAdapter: ListRecipeAdapter by lazy {
        ListRecipeAdapter {
            navigateToDetail(it)
        }
    }

    private val listRecommendedAdapter: ListRecipeAdapter by lazy {
        ListRecipeAdapter {
            navigateToDetail(it)
        }
    }

    private val listCategoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter { id, name ->
            navigateToCategory(id, name)
        }
    }

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

            svRecipe.setOnClickListener {
                navigateToSearch()
            }

            svRecipe.setOnQueryTextFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    navigateToSearch()
                }
            }

            rvPopularRecipe.apply {
                adapter = listRecipeAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                isNestedScrollingEnabled = true
            }

            chipGroupCategory.apply {
                chipCategoryFood.isChecked = true
                isNestedScrollingEnabled = true
            }

            rvRecommendedRecipe.apply {
                adapter = listRecommendedAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                isNestedScrollingEnabled = true
            }

            rvUserTaste.apply {
                adapter = listCategoryAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                isNestedScrollingEnabled = true
            }
        }

    }

    override fun initActions() {
        binding.apply {
            chipCategoryFood.setOnClickListener {
                selectedCategory = chipCategoryFood.text.toString()
                viewModel.getRecipes(mainCategory = selectedCategory)
            }

            chipCategoryDrink.setOnClickListener {
                selectedCategory = chipCategoryDrink.text.toString()
                viewModel.getRecipes(mainCategory = selectedCategory)
            }
        }
    }

    override fun initProcess() {
        viewModel.getRecipes()
        viewModel.getRecipes(mainCategory = selectedCategory)
        viewModel.getCategories()
    }
    override fun initObservers() {
        viewModel.popularRecipeResult.observeResponse(
            viewLifecycleOwner,
            loading = {
                showLoadingDialog()
            },
            success = {
                hideLoadingDialog()
                listRecipeAdapter.setData(it.data)
            },
            empty = {
                hideLoadingDialog()
            },
            error = {
                hideLoadingDialog()
            }
        )

        viewModel.recommendedRecipeResult.observeResponse(
            viewLifecycleOwner,
            loading = {
                showLoadingDialog()
            },
            success = {
                hideLoadingDialog()
                listRecommendedAdapter.setData(it.data)
            },
            empty = {
                hideLoadingDialog()
            },
            error = {
                hideLoadingDialog()
            }
        )

        viewModel.categoryResult.observeResponse(
            viewLifecycleOwner,
            loading = {
                showLoadingDialog()
            },
            success = {
                hideLoadingDialog()
                listCategoryAdapter.setData(it.data)
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
            HomeFragmentDirections.actionHomeFragmentToDetailRecipeFragment(
                slug
            )
        )
    }

    private fun navigateToCategory(secondCategoryId: String, name: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToCategoryFragment(
                secondCategoryId,
                name
            )
        )
    }

    private fun navigateToSearch() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToSearchRecipeFragment()
        )
    }

}