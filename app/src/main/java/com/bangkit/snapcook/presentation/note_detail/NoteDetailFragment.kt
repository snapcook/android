package com.bangkit.snapcook.presentation.note_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentNoteDetailBinding
import com.bangkit.snapcook.presentation.note_detail.adapter.ShoppingNoteAdapter
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.setPopBackEnabled
import com.bangkit.snapcook.utils.extension.showYesNoDialog
import org.koin.android.ext.android.inject
import timber.log.Timber

class NoteDetailFragment : BaseFragment<FragmentNoteDetailBinding>() {

    private val viewModel: NoteDetailViewModel by inject()
    private var groupId = ""

    private val groceryIngredientGroupAdapter: ShoppingNoteAdapter by lazy {
        ShoppingNoteAdapter { grocery, boolean ->
            viewModel.updateCompleted(grocery, boolean)
        }
    }

    private val grocerySpicesGroupAdapter: ShoppingNoteAdapter by lazy {
        ShoppingNoteAdapter { grocery, boolean ->
            viewModel.updateCompleted(grocery, boolean)
        }
    }

    private val groceryUtensilGroupAdapter: ShoppingNoteAdapter by lazy {
        ShoppingNoteAdapter { grocery, boolean ->
            viewModel.updateCompleted(grocery, boolean)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentNoteDetailBinding {
        return FragmentNoteDetailBinding.inflate(inflater, container, false)
    }

    override fun initIntent() {
        val safeArgs = arguments?.let { NoteDetailFragmentArgs.fromBundle(it) }
        groupId = safeArgs?.groupId ?: ""
    }

    override fun initUI() {
        binding.apply {
            toolBar.setPopBackEnabled()
            btnDelete.popClick {
                showYesNoDialog(
                    title = getString(R.string.title_delete_note),
                    message = getString(R.string.desc_delete_note),
                    onYes = {
                        viewModel.deleteGrocery(groupId)
                        findNavController().popBackStack()
                    }
                )
            }
            rvIngredients.apply {
                adapter = groceryIngredientGroupAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            rvSpices.apply {
                adapter = grocerySpicesGroupAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            rvUtensils.apply {
                adapter = groceryUtensilGroupAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
        viewModel.getIngredientsGrocery(groupId).observe(viewLifecycleOwner) {
            groceryIngredientGroupAdapter.setData(it)
        }
        viewModel.getUtensilsGrocery(groupId).observe(viewLifecycleOwner) {
            groceryUtensilGroupAdapter.setData(it)
        }
        viewModel.getSpicesGrocery(groupId).observe(viewLifecycleOwner) {
            grocerySpicesGroupAdapter.setData(it)
        }
        viewModel.isAllNoteCompleted.observe(viewLifecycleOwner){
            Timber.d("IS ALL DONE : $it")
        }
    }

}