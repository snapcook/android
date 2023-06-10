package com.bangkit.snapcook.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.databinding.FragmentDetailRecipeBinding
import com.bangkit.snapcook.presentation.detail.adapter.ListStepsAdapter
import com.bangkit.snapcook.presentation.detail.adapter.ListStringAdapter
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.setImageUrl
import org.koin.android.ext.android.inject
import timber.log.Timber

class DetailRecipeFragment : BaseFragment<FragmentDetailRecipeBinding>() {

    private val viewModel: DetailRecipeViewModel by inject()

    private var slug = ""
    private var recipe: Recipe? = null

    private val listSpicesAdapter: ListStringAdapter by lazy {
        ListStringAdapter()
    }

    private val listIngredientAdapter: ListStringAdapter by lazy {
        ListStringAdapter()
    }

    private val listStepsAdapter: ListStepsAdapter by lazy {
        ListStepsAdapter()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentDetailRecipeBinding {
        return FragmentDetailRecipeBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            btnBack.setOnClickListener {
                it.findNavController().popBackStack()
            }

            rvIngredient.apply {
                adapter = listIngredientAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                isNestedScrollingEnabled = false
            }

            rvSpices.apply {
                adapter = listSpicesAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                isNestedScrollingEnabled = false
            }

            rvSteps.apply {
                adapter = listStepsAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                isNestedScrollingEnabled = false
            }

            btnStartCooking.popClick {

            }
        }
    }

    override fun initIntent() {
        val safeArgs = arguments?.let { DetailRecipeFragmentArgs.fromBundle(it) }
        slug = safeArgs?.slug ?: ""
    }

    override fun initProcess() {
        viewModel.getRecipeDetail(slug)
    }

    override fun initObservers() {
        viewModel.recipeDetailResult.observe(viewLifecycleOwner) { response ->
            Timber.d("Response is $response")
            when (response) {
                is ApiResponse.Success -> {
                    hideLoadingDialog()
                    recipe = response.data
                    binding.apply {
                        viewModel.checkIsGroceryGroupExist(recipe?.id ?: "")
                        imgFood.setImageUrl(recipe?.photo)
                        imgProfile.setImageUrl(recipe?.author?.photo)
                        tvTitle.text = recipe?.title
                        tvUserName.text = recipe?.author?.name
                        tvUserSlug.text = recipe?.author?.slug
                        tvTotalBookmark.text = recipe?.totalBookmark?.toBookmarkCount()
                        tvTimer.text = recipe?.estimatedTime?.toHoursAndMinutes()
                        tvStory.text = recipe?.description
                        tvPortion.text = recipe?.totalServing?.toPortionString()
                        listIngredientAdapter.setData(recipe!!.mainIngredients)
                        listSpicesAdapter.setData(recipe?.spices ?: listOf())
                        listStepsAdapter.setData(recipe?.steps ?: listOf())

                        btnBuyIngredient.popClick {
                            navigateToAddToGrocery(
                                recipe!!

                            )
                        }
                    }

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

        viewModel.isGroceryGroupExist.observe(viewLifecycleOwner) { isExist ->
            binding.btnBuyIngredient.isEnabled = !isExist
            if (isExist) {
                binding.btnBuyIngredient.text = getString(R.string.label_already_added)
            } else {
                binding.btnBuyIngredient.text = getString(R.string.action_buy_ingredient)
            }
        }
    }

    private fun Int.toHoursAndMinutes(): String {
        val hours = this / 60
        val minutes = this % 60

        return if (hours != 0 && minutes != 0) {
            "$hours jam $minutes menit"
        } else if (hours != 0) {
            "$hours jam"
        } else {
            "$minutes menit"
        }
    }

    private fun navigateToAddToGrocery(
        recipe: Recipe,
    ) {
        val action =
            DetailRecipeFragmentDirections.actionDetailRecipeFragmentToAddToGroceryFragment(
                recipe.fullIngredients.toTypedArray(),
                recipe.spices.toTypedArray(),
                recipe.id,
                recipe.slug,
                recipe.photo,
                recipe.title,
            )
        binding.root.findNavController().navigate(action)
    }

    private fun Int.toPortionString(): String {
        return "$this porsi"
    }

    private fun Int.toBookmarkCount(): String {
        return "$this bookmark"
    }
}