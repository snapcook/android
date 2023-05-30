package com.bangkit.snapcook.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.databinding.FragmentSearchRecipeBinding
import com.bangkit.snapcook.presentation.home.HomeFragmentDirections
import com.bangkit.snapcook.presentation.home.adapter.ListRecipeAdapter
import com.bangkit.snapcook.presentation.search.adapter.ListRecipeDetailAdapter
import com.bangkit.snapcook.utils.extension.*
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.util.*

class SearchRecipeFragment : BaseFragment<FragmentSearchRecipeBinding>() {

    private val viewModel: SearchRecipeViewModel by inject()

    private val listRecipeDetailAdapter: ListRecipeDetailAdapter by lazy {
        ListRecipeDetailAdapter(
            onClick = { navigateToDetail(it) },
            onBookmarkClick = { recipe ->
                if (recipe.bookmarkId != null) {
                    viewModel.removeBookmark(recipe.bookmarkId!!)
                    recipe.bookmarkId = null
                    binding.root.showSnackBar("Resep dihapus dari Bookmark.")
                } else {
                    viewModel.addBookmark(recipe.id)
                }
            }
        )
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSearchRecipeBinding {
        return FragmentSearchRecipeBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            toolBar.setPopBackEnabled()
            tvSearchKeyword.hide()
            svRecipe.apply {
                isIconified = false
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        clearFocus()
                        viewModel.searchRecipe(null, null, null, query)
                        tvSearchKeyword.show()
                        tvSearchKeyword.text = capitalizeWord(query)
                        return true
                    }
                    override fun onQueryTextChange(newText: String): Boolean {
                        tvSearchKeyword.text = capitalizeWord(newText)
                        return false
                    }
                })
            }

            rvSearchRecipe.hide()
            rvSearchRecipe.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = listRecipeDetailAdapter
            }
        }

    }

    override fun initProcess() {}

    override fun initObservers() {
        viewModel.searchResult.observeSingleEvent(viewLifecycleOwner,
            loading = {
                showLoadingDialog()
            },
            success = { response ->
                hideLoadingDialog()
                val recipes = response.data
                listRecipeDetailAdapter.setData(recipes)
                binding.apply {
                    rvSearchRecipe.show()
                    tvInfo.text = "Resep yang ditemukan"
                }
            },
            error = {response ->
                hideLoadingDialog()
                binding.root.showSnackBar(response.errorMessage)
            },
        )
    }

    private fun capitalizeWord(word: String): String {
        return word.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    }

    private fun navigateToDetail(slug: String) {
        findNavController().navigate(
            SearchRecipeFragmentDirections.actionSearchRecipeFragmentToDetailRecipeFragment(
                slug
            )
        )
    }
}