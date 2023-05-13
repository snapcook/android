package com.bangkit.snapcook.presentation.add_recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentAddRecipeBinding
import com.bangkit.snapcook.presentation.add_recipe.adapter.AddIngredientAdapter
import com.bangkit.snapcook.presentation.add_recipe.adapter.AddSpiceAdapter
import com.bangkit.snapcook.presentation.add_recipe.adapter.AddStepAdapter
import com.bangkit.snapcook.utils.extension.popClick
import org.koin.android.ext.android.inject
import timber.log.Timber

class AddRecipeFragment : BaseFragment<FragmentAddRecipeBinding>() {

    private val viewModel: AddRecipeViewModel by inject()
    private val addIngredientAdapter: AddIngredientAdapter by lazy {
        AddIngredientAdapter()
    }

    private val addSpiceAdapter: AddSpiceAdapter by lazy {
        AddSpiceAdapter()
    }

    private val addStepAdapter: AddStepAdapter by lazy {
        AddStepAdapter()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAddRecipeBinding {
        return FragmentAddRecipeBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            rvIngredient.apply {
                adapter = addIngredientAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                isNestedScrollingEnabled = false
            }

            rvSpices.apply {
                adapter = addSpiceAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                isNestedScrollingEnabled = false
            }

            rvSteps.apply {
                adapter = addStepAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                isNestedScrollingEnabled = false
            }

            btnAddMainIngredient.popClick {
                addIngredientAdapter.addData()
            }

            btnAddSpice.popClick {
                addSpiceAdapter.addData()
            }

            btnAddStep.popClick {
                addStepAdapter.addData()
            }

            btnSave.popClick {
                val result = addIngredientAdapter.retrieveResult()
                Timber.d("RESULT $result")
                tvTest.text = result
            }
        }

        addIngredientAdapter.itemTouchHelper.attachToRecyclerView(binding.rvIngredient)
        addSpiceAdapter.itemTouchHelper.attachToRecyclerView(binding.rvSpices)
        addStepAdapter.itemTouchHelper.attachToRecyclerView(binding.rvSteps)
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }
}