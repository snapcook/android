package com.bangkit.snapcook.presentation.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.databinding.FragmentBookmarkBinding
import com.bangkit.snapcook.presentation.search.SearchRecipeFragmentDirections
import com.bangkit.snapcook.presentation.search.adapter.ListRecipeDetailAdapter
import com.bangkit.snapcook.utils.extension.showSnackBar
import org.koin.android.ext.android.inject
import timber.log.Timber

class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>() {

    private val viewModel: BookmarkViewModel by inject()

    private val listRecipeDetailAdapter: ListRecipeDetailAdapter by lazy {
        ListRecipeDetailAdapter(
            onClick = {
                navigateToDetail(it)
                },
            onBookmarkClick = { recipe ->
                Timber.d("Bookmark icon clicked")
                if (recipe.bookmarkId != null) {
                    Timber.d("RecipeBookmarkedId = ${recipe.bookmarkId}")
                    viewModel.removeBookmark(recipe.bookmarkId!!)
                    recipe.bookmarkId = null
                    Timber.d("RecipeBookmarkedId after remove = ${recipe.bookmarkId}")
                    //viewModel.getBookmarkedRecipe()
                    Timber.d("RecipeBookmarkedId after viewmodel observed again = ${recipe.bookmarkId}")
                    binding.root.showSnackBar("Resep dihapus dari Bookmark.")
                }
            }
        )
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentBookmarkBinding {
        return FragmentBookmarkBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        viewModel.getBookmarkedRecipe()

        binding.apply {
            rvBookmarkedRecipe.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = listRecipeDetailAdapter
            }
        }
    }

    override fun initProcess() {}

    override fun initObservers() {
        viewModel.getBookmarkResult.observe(viewLifecycleOwner){ response ->
            when (response) {
                is ApiResponse.Success -> {
                    hideLoadingDialog()
                    val bookmarkResponses = response.data
                    val recipeList = mutableListOf<Recipe>()

                    for (bookmarkResponse in bookmarkResponses) {
                        val recipe = bookmarkResponse.recipe
                        recipe.bookmarkId = bookmarkResponse.id
                        recipeList.add(recipe)
                    }

                    listRecipeDetailAdapter.setData(recipeList)
                }
                is ApiResponse.Loading -> {
                    showLoadingDialog()
                }
                is ApiResponse.Error -> {
                    hideLoadingDialog()
                    Timber.d("${response.errorMessage}")
                    binding.root.showSnackBar(response.errorMessage)
                }
                is ApiResponse.Empty -> {
                    // Handle empty state
                }
            }
        }
    }

    private fun navigateToDetail(slug: String) {
        findNavController().navigate(
            BookmarkFragmentDirections.actionBookmarkFragmentToDetailRecipeFragment(
                slug
            )
        )
    }

}