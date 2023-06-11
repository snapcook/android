package com.bangkit.snapcook.presentation.note_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentNoteDetailBinding
import com.bangkit.snapcook.presentation.note.NoteFragmentDirections
import com.bangkit.snapcook.presentation.note_detail.adapter.ShoppingNoteAdapter
import com.bangkit.snapcook.utils.extension.gone
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.setPopBackEnabled
import com.bangkit.snapcook.utils.extension.showYesNoDialog
import com.bangkit.snapcook.utils.extension.slowShow
import org.koin.android.ext.android.inject
import timber.log.Timber

class NoteDetailFragment : BaseFragment<FragmentNoteDetailBinding>() {

    private val viewModel: NoteDetailViewModel by inject()
    private var groupId = ""
    private var slug = ""

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
        slug = safeArgs?.slug ?: ""
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

            btnStartCooking.popClick {
                navigateToRecipeDetail(slug)
            }
        }
    }

    override fun initProcess() {
        viewModel.checkStatus(groupId)
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
        viewModel.isAllNoteCompleted.observe(viewLifecycleOwner){done ->
            Timber.d("IS ALL DONE : $done")
            if (done) {
                binding.btnStartCooking.isEnabled = true
                binding.btnStartCooking.slowShow()
            } else {
                binding.btnStartCooking.isEnabled = false
                binding.btnStartCooking.gone()
            }
        }
    }

    private fun navigateToRecipeDetail(slug: String) {
        findNavController().navigate(
            NoteDetailFragmentDirections.actionNoteDetailFragmentToDetailRecipeFragment(
                slug
            )
        )
    }

}