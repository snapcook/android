package com.bangkit.snapcook.presentation.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentCategoryBinding
import com.bangkit.snapcook.presentation.home.HomeFragmentDirections
import com.bangkit.snapcook.presentation.search.adapter.ListRecipeDetailAdapter
import com.bangkit.snapcook.utils.extension.*
import org.koin.android.ext.android.inject
import timber.log.Timber


class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {

    private val viewModel: CategoryViewModel by inject()
    private var categoryId = ""
    private var name = ""

    private val listRecipeDetailAdapter: ListRecipeDetailAdapter by lazy {
        ListRecipeDetailAdapter(
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

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentCategoryBinding {
        return FragmentCategoryBinding.inflate(inflater, container, false)
    }

    override fun initIntent() {
        val safeArgs = arguments?.let { CategoryFragmentArgs.fromBundle(it) }
        categoryId = safeArgs?.categoryId ?: ""
        name = safeArgs?.name ?: ""
    }

    override fun initUI() {
       binding.apply {
           toolBar.setPopBackEnabled()
           toolBar.title = name
           rvCategory.hide()
           rvCategory.apply {
               layoutManager =
                   LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
               adapter = listRecipeDetailAdapter
           }
       }
    }

    override fun initProcess() {
        viewModel.searchCategory(categoryId)
   }

    override fun initObservers() {
        viewModel.categoryResult.observeResponse(viewLifecycleOwner,
            loading = {
                showLoading(true)
            },
            success = { response ->
                showLoading(false)
                val recipes = response.data

                Timber.d("HAVE DATA ${recipes.first().title}")

                listRecipeDetailAdapter.setData(recipes)
                binding.apply {
                    emptyListLayout.root.gone()
                    rvCategory.show()
                }
            },
            error = {response ->
                showLoading(false)
                binding.root.showSnackBar(response.errorMessage)
            },
            empty = {
                showLoading(false)
                binding.apply {
                    rvCategory.hide()
                    emptyListLayout.root.show()
                }
            }
        )
    }

    private fun navigateToDetail(slug: String) {
        findNavController().navigate(
            CategoryFragmentDirections.actionCategoryFragmentToDetailRecipeFragment(
                slug
            )
        )
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmeringLoadingCategory.startShimmer()
                shimmeringLoadingCategory.showShimmer(true)
                shimmeringLoadingCategory.show()
                tvCategory.gone()
                frameRecipe.gone()
            } else {
                shimmeringLoadingCategory.stopShimmer()
                shimmeringLoadingCategory.showShimmer(false)
                shimmeringLoadingCategory.gone()
                tvCategory.slowShow()
                frameRecipe.slowShow()
            }
        }
    }

}