package com.bangkit.snapcook.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.data.model.User
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.databinding.FragmentHomeBinding
import com.bangkit.snapcook.presentation.home.adapter.CategoryAdapter
import com.bangkit.snapcook.presentation.home.adapter.ListRecipeAdapter
import com.bangkit.snapcook.presentation.modal.utensils.UtensilsAdapter
import com.bangkit.snapcook.utils.constant.AnimationConstants
import com.bangkit.snapcook.utils.extension.*
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

            bannerImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.logo_snapcook_full))

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
                chipCategoryFood.isSelected = true
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
                chipCategoryFood.isSelected = true
                chipCategoryDrink.isSelected = false
                selectedCategory = chipCategoryFood.text.toString()
                viewModel.getRecipes(mainCategory = selectedCategory)
            }

            chipCategoryDrink.setOnClickListener {
                chipCategoryDrink.isSelected = true
                chipCategoryFood.isSelected = false
                selectedCategory = chipCategoryDrink.text.toString()
                viewModel.getRecipes(mainCategory = selectedCategory)
            }
        }
    }

    override fun initProcess() {
        viewModel.getRecipesOrdered()
        viewModel.getRecipes(mainCategory = selectedCategory)
        viewModel.getCategories()
        viewModel.getProfile()
    }
    @SuppressLint("SetTextI18n")
    override fun initObservers() {
        viewModel.popularRecipeResult.observeResponse(
            viewLifecycleOwner,
            loading = {
                showLoading(true)
            },
            success = {
                showLoading(false)
                listRecipeAdapter.setData(it.data)
            },
            empty = {
                showLoading(false)
            },
            error = {
                showLoading(false)
            }
        )

        viewModel.recommendedRecipeResult.observeResponse(
            viewLifecycleOwner,
            loading = {
                showLoadingRecommended(true)
            },
            success = {
                showLoadingRecommended(false)
                listRecommendedAdapter.setData(it.data)
            },
            empty = {
                showLoadingRecommended(false)
            },
            error = {
                showLoadingRecommended(false)
            }
        )

        viewModel.categoryResult.observeResponse(
            viewLifecycleOwner,
            loading = {
                showLoading(true)
            },
            success = {
                showLoading(false)
                listCategoryAdapter.setData(it.data)
            },
            empty = {
                showLoading(false)
            },
            error = {
                showLoading(false)
            }
        )

        viewModel.profileResult.observeResponse(
            viewLifecycleOwner,
            loading = {
                showLoading(true)
            },
            success = {
                showLoading(false)
                val user: User = it.data

                binding.apply {
                    imgUser.setImageUrl(user.photo)
                    val name = extractFirstName(user.name)
                    tvName.text = getString(R.string.welcome_name, name)
                }

            },
            error = {
                showLoading(false)
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

    private fun extractFirstName(fullname: String) : String {
        return fullname.split(" ")[0]
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmeringLoadingHome.startShimmer()
                shimmeringLoadingHome.showShimmer(true)
                shimmeringLoadingHome.show()
                layoutHome.gone()
            } else {
                shimmeringLoadingHome.stopShimmer()
                shimmeringLoadingHome.showShimmer(false)
                shimmeringLoadingHome.gone()
                layoutHome.slowShow()
            }
        }
    }

    private fun showLoadingRecommended(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmeringLoadingRecommended.startShimmer()
                shimmeringLoadingRecommended.showShimmer(true)
                shimmeringLoadingRecommended.show()
                rvRecommendedRecipe.gone()
            } else {
                shimmeringLoadingRecommended.stopShimmer()
                shimmeringLoadingRecommended.showShimmer(false)
                shimmeringLoadingRecommended.gone()
                rvRecommendedRecipe.slowShow()
            }
        }
    }

}


