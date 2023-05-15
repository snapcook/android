package com.bangkit.snapcook.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentSearchRecipeBinding
import com.bangkit.snapcook.utils.extension.setPopBackEnabled
import org.koin.android.ext.android.inject

class SearchRecipeFragment : BaseFragment<FragmentSearchRecipeBinding>() {

    private val viewModel: SearchRecipeViewModel by inject()

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
            svRecipe.apply {
                requestFocus()
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        clearFocus()
                        return true
                    }
                    override fun onQueryTextChange(newText: String): Boolean {
                        return false
                    }
                })
            }

            rvSearchRecipe.apply {

            }
        }

    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }

}