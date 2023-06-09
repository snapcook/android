package com.bangkit.snapcook.presentation.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentBookmarkBinding
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
                showLoading(true)
            },
            success = { response ->
                showLoading(false)
                val bookmarkRecipes = response.data

                listRecipeDetailAdapter.setData(bookmarkRecipes)
                binding.apply {
                    emptyListLayout.root.gone()
                    rvBookmarkedRecipe.show()
                }
            },
            error = {response ->
                showLoading(false)
                Timber.d(response.errorMessage)
                binding.apply {
                    emptyListLayout.root.show()
                    root.showSnackBar(response.errorMessage)
                }
            },
            empty = {
                showLoading(false)
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

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmeringLoadingBookmark.startShimmer()
                shimmeringLoadingBookmark.showShimmer(true)
                shimmeringLoadingBookmark.show()
                tvBookmark.gone()
                frameRecipe.gone()
            } else {
                shimmeringLoadingBookmark.stopShimmer()
                shimmeringLoadingBookmark.showShimmer(false)
                shimmeringLoadingBookmark.gone()
                tvBookmark.slowShow()
                frameRecipe.slowShow()
            }
        }
    }

}