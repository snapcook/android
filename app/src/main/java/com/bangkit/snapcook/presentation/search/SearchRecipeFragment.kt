package com.bangkit.snapcook.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentSearchRecipeBinding
import com.bangkit.snapcook.presentation.search.adapter.ListRecipeDetailAdapter
import com.bangkit.snapcook.utils.extension.*
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.util.Locale

class SearchRecipeFragment : BaseFragment<FragmentSearchRecipeBinding>() {

    private val viewModel: SearchRecipeViewModel by inject()

    private var typedQuery: String? = null

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
    ): FragmentSearchRecipeBinding {
        return FragmentSearchRecipeBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            toolBar.setPopBackEnabled()
            svRecipe.slowShow()
            tvInfo.slowShow()

            tvSearchKeyword.hide()
            rvSearchRecipe.hide()
            tvInfo.text = "Carilah resep yang kamu inginkan."

            svRecipe.apply {
                isIconified = false
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        clearFocus()
                        viewModel.searchRecipe(query)
                        tvSearchKeyword.show()
                        typedQuery = query
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
        viewModel.searchResult.observeResponse(viewLifecycleOwner,
            loading = {
                showLoading(true)
            },
            success = { response ->
                showLoading(false)
                val recipes = response.data

                Timber.d("HAVE DATA ${recipes.first().title}")

                listRecipeDetailAdapter.setData(recipes)
                binding.apply {
                    rvSearchRecipe.show()
                    emptyListLayout.root.gone()
                    tvInfo.text = "Resep yang ditemukan"
                }
            },
            error = {response ->
                showLoading(false)
                binding.apply {
                    root.showSnackBar(response.errorMessage)
                    emptyListLayout.root.show()
                }
            },
            empty = {
                showLoading(false)
                binding.apply {
                    tvInfo.text = "Tidak ada resep seperti ini."
                    rvSearchRecipe.hide()
                    emptyListLayout.root.show()
                }
            }
        )
    }

    override fun onResume() {
        binding.apply {
            rvSearchRecipe.hide()
            tvInfo.text = "Carilah resep yang kamu inginkan."
            if(typedQuery != null){
                tvSearchKeyword.text = capitalizeWord(typedQuery!!)
                viewModel.searchRecipe(typedQuery)
            }
        }
        super.onResume()
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

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmeringLoadingSearch.startShimmer()
                shimmeringLoadingSearch.showShimmer(true)
                shimmeringLoadingSearch.show()
                toolBar.gone()
                svRecipe.gone()
                tvInfo.gone()
                tvSearchKeyword.gone()
                rvSearchRecipe.gone()
            } else {
                shimmeringLoadingSearch.stopShimmer()
                shimmeringLoadingSearch.showShimmer(false)
                shimmeringLoadingSearch.gone()
                toolBar.slowShow()
                svRecipe.slowShow()
                tvInfo.slowShow()
                tvSearchKeyword.slowShow()
                rvSearchRecipe.slowShow()
            }
        }
    }
}