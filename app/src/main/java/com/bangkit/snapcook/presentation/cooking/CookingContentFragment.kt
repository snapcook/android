package com.bangkit.snapcook.presentation.cooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentCookingContentBinding
import com.bangkit.snapcook.presentation.cooking.adapter.CookingIngredientsAdapter
import com.bangkit.snapcook.utils.constant.GroceryConstants.END_COOKING_STEP
import com.bangkit.snapcook.utils.constant.GroceryConstants.START_COOKING_STEP
import com.bangkit.snapcook.utils.extension.gone
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.show

class CookingContentFragment : BaseFragment<FragmentCookingContentBinding>() {

    private val ingredientsAdapter: CookingIngredientsAdapter by lazy {
        CookingIngredientsAdapter()
    }

    private val spicesAdapter: CookingIngredientsAdapter by lazy {
        CookingIngredientsAdapter()
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentCookingContentBinding {
        return FragmentCookingContentBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        val step = arguments?.getString("step") ?: ""
        val ingredients = arguments?.getStringArray("ingredients") ?: arrayOf()
        val spices = arguments?.getStringArray("spices") ?: arrayOf()

        binding.apply {
            if (step == START_COOKING_STEP){
                content.show()
                tvStep.gone()
                btnBackToRecipe.gone()

                rvIngredients.adapter = ingredientsAdapter
                rvIngredients.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                rvSpices.adapter = spicesAdapter
                rvSpices.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                ingredientsAdapter.setData(ingredients.toList())
                spicesAdapter.setData(spices.toList())
            } else if (step == END_COOKING_STEP){
                content.gone()
                btnBackToRecipe.show()
                btnBackToRecipe.popClick {
                    findNavController().popBackStack()
                }
                tvStep.text = getString(com.bangkit.snapcook.R.string.message_end_cooking_step)
            }
            else {
                btnBackToRecipe.gone()
                content.gone()
                tvStep.text = step
            }
        }

    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }

}