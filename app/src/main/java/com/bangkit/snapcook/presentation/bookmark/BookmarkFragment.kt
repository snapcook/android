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
import com.bangkit.snapcook.utils.extension.*
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
                binding.root.showSnackBar("Resep dihapus dari Bookmark.")
                viewModel.removeBookmark(recipe.id ?: "")
                viewModel.getBookmarkedRecipe()
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
        binding.apply {
            rvBookmarkedRecipe.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = listRecipeDetailAdapter
            }
        }
    }

    override fun initProcess() {
        viewModel.getBookmarkedRecipe()
    }

    override fun initObservers() {
        viewModel.getBookmarkResult.observeResponse(viewLifecycleOwner,
            loading = {
                showLoadingDialog()
            },
            success = { response ->
                hideLoadingDialog()
                val bookmarkRecipes = response.data

                listRecipeDetailAdapter.setData(bookmarkRecipes)
                binding.apply {
                    emptyListLayout.root.gone()
                    rvBookmarkedRecipe.show()
                }
            },
            error = {response ->
                hideLoadingDialog()
                Timber.d(response.errorMessage)
                binding.apply {
                    emptyListLayout.root.show()
                    root.showSnackBar(response.errorMessage)
                }
            },
            empty = {
                hideLoadingDialog()
                binding.apply{
                    rvBookmarkedRecipe.hide()
                    emptyListLayout.root.show()
                }
            }
        )
    }

    private fun navigateToDetail(slug: String) {
        findNavController().navigate(
            BookmarkFragmentDirections.actionBookmarkFragmentToDetailRecipeFragment(
                slug
            )
        )
    }

}