package com.bangkit.snapcook.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.databinding.FragmentHomeBinding
import com.bangkit.snapcook.presentation.home.adapter.ListRecipeAdapter
import com.bangkit.snapcook.utils.extension.closeApp
import com.bangkit.snapcook.utils.extension.showSnackBar
import com.bangkit.snapcook.utils.extension.showYesNoDialog
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showYesNoDialog(
                        title = getString(R.string.title_close_app),
                        message = getString(R.string.message_close_app),
                        onYes = {
                            closeApp()
                        }
                    )
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
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
                viewModel.getRecipes(null, selectedCategory, null, null)
            }

            chipCategoryDrink.setOnClickListener {
                selectedCategory = chipCategoryDrink.text.toString()
                viewModel.getRecipes(null, selectedCategory, null, null)
            }
        }
    }

    override fun initProcess() {
        viewModel.getRecipes()
        viewModel.getRecipes(null, selectedCategory, null, null)
    }
    override fun initObservers() {
        viewModel.popularRecipeResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    hideLoadingDialog()
                    val recipes = response.data
                    listRecipeAdapter.setData(recipes)
                }
                is ApiResponse.Loading -> {
                    showLoadingDialog()
                }
                is ApiResponse.Error -> {
                    hideLoadingDialog()
                    binding.root.showSnackBar(response.errorMessage)
                }
                is ApiResponse.Empty -> {
                    // Handle empty state
                }
            }
        }

        viewModel.recommendedRecipeResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    hideLoadingDialog()
                    val recipes = response.data
                    listRecommendedAdapter.setData(recipes)
                }
                is ApiResponse.Loading -> {
                    showLoadingDialog()
                }
                is ApiResponse.Error -> {
                    hideLoadingDialog()
                }
                is ApiResponse.Empty -> {
                    // Handle empty state
                }
            }
        }
    }

    private fun navigateToDetail(slug: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailRecipeFragment(
                slug
            )
        )
    }

    private fun navigateToSearch() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToSearchRecipeFragment()
        )
    }

}