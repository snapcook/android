package com.bangkit.snapcook.presentation.add_to_grocery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentAddToGroceryBinding
import com.bangkit.snapcook.presentation.MainActivity
import com.bangkit.snapcook.presentation.add_to_grocery.adapter.AddToGroceryAdapter
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.showSnackBar
import org.koin.android.ext.android.inject

class AddToGroceryFragment : BaseFragment<FragmentAddToGroceryBinding>() {

    private val viewModel: AddToGroceryViewModel by inject()
    private var groupId = ""
    private var title = ""
    private var photo = ""
    private var slug = ""
    private var ingredients: List<String> = listOf()
    private var spices: List<String> = listOf()

    private val ingredientAdapter: AddToGroceryAdapter by lazy {
        AddToGroceryAdapter{
            binding.cbSelectAllIngredients.isChecked = ingredientAdapter.checkIsAllSelected()
        }
    }

    private val spiceAdapter: AddToGroceryAdapter by lazy {
        AddToGroceryAdapter{
            binding.cbSelectAllSpices.isChecked = spiceAdapter.checkIsAllSelected()
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentAddToGroceryBinding {
        return FragmentAddToGroceryBinding.inflate(inflater, container, false)
    }

    override fun initIntent() {
        val safeArgs = arguments?.let { AddToGroceryFragmentArgs.fromBundle(it) }
        groupId = safeArgs?.recipeId ?: ""
        ingredients = safeArgs?.ingredients?.toList() ?: listOf()
        spices = safeArgs?.spices?.toList() ?: listOf()
        photo = safeArgs?.photo ?: ""
        title = safeArgs?.title ?: ""
        slug = safeArgs?.slug ?: ""
    }

    override fun initUI() {
        binding.apply {
            toolBar.title = getString(R.string.title_add_to_grocery)
            toolBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            rvIngredients.adapter = ingredientAdapter
            rvIngredients.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvSpices.adapter = spiceAdapter
            rvSpices.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            cbSelectAllIngredients.setOnClickListener {
                if (cbSelectAllIngredients.isChecked)
                    ingredientAdapter.selectAll()
                else
                    ingredientAdapter.clearSelectedData()
            }

            cbSelectAllSpices.setOnClickListener {
                if (cbSelectAllSpices.isChecked)
                    spiceAdapter.selectAll()
                else
                    spiceAdapter.clearSelectedData()
            }

            btnSave.popClick {
                viewModel.addToGroceryList(
                    slug,
                    groupId,
                    title,
                    photo,
                    ingredientAdapter.getSelectedData(),
                    spiceAdapter.getSelectedData()
                )
//                (activity as? MainActivity)?.navigateToNavBarDestination(R.id.noteFragment)

                findNavController().navigate(R.id.action_addToGroceryFragment_to_noteFragment)
                root.showSnackBar(getString(R.string.message_success_added_to_grocery_list))
            }
        }

        ingredientAdapter.setData(ingredients)
        spiceAdapter.setData(spices)
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }

}