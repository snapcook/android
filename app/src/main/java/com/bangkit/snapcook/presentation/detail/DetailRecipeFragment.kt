package com.bangkit.snapcook.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.databinding.FragmentDetailRecipeBinding
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.setImageUrl
import com.bangkit.snapcook.utils.extension.setPopBackEnabled
import org.koin.android.ext.android.inject
import timber.log.Timber

class DetailRecipeFragment : BaseFragment<FragmentDetailRecipeBinding>() {

    private val viewModel: DetailRecipeViewModel by inject()

    private var slug = ""

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailRecipeBinding {
        return FragmentDetailRecipeBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            toolBar.setPopBackEnabled()
            btnBookmark.popClick {

            }
            btnBuyIngredient.popClick {

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
                    val recipe = response.data
                    binding.apply {
                        imgFood.setImageUrl(recipe.photo)
                        imgProfile.setImageUrl(recipe.author.photo)
                        tvTitle.text = recipe.title
                        tvUserName.text = recipe.author.name
                        tvUserSlug.text = recipe.author.slug
                        tvTimer.text = recipe.estimatedTime.toHoursAndMinutes()
                        tvStory.text = recipe.description
                        tvPortion.text = recipe.totalServing.toPortionString()
                        //recycler view to do
                    }

                }

                is ApiResponse.Loading -> {
                    // Handle loading state
                }
                is ApiResponse.Error -> {
                    // Handle error state
                }
                is ApiResponse.Empty -> {
                    // Handle empty state
                }

            }
        }
    }

    fun Int.toHoursAndMinutes(): String {
        val hours = this / 60
        val minutes = this % 60

        return if (hours != 0) {
            "$hours jam $minutes menit"
        } else {
            "$minutes menit"
        }
    }

    fun Int.toPortionString(): String {
        return "$this porsi"
    }
}