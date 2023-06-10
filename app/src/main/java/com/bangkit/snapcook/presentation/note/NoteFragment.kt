package com.bangkit.snapcook.presentation.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentNoteBinding
import com.bangkit.snapcook.presentation.note.adapter.GroceryGroupAdapter
import com.bangkit.snapcook.utils.extension.closeApp
import com.bangkit.snapcook.utils.extension.observeResponse
import com.bangkit.snapcook.utils.extension.showYesNoDialog
import org.koin.android.ext.android.inject
import timber.log.Timber

class NoteFragment : BaseFragment<FragmentNoteBinding>() {
    private val viewModel: NoteViewModel by inject()

    private val groceryGroupAdapter: GroceryGroupAdapter by lazy {
        GroceryGroupAdapter(
            onClickDetail = {groupId ->
                navigateToDetail(groupId)
            },
            onClickStartCooking = {slug ->
                navigateToRecipeDetail(slug)
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
        savedInstanceState: Bundle?,
    ): FragmentNoteBinding {
        return FragmentNoteBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            rvGroceryGroup.apply {
                adapter = groceryGroupAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun initProcess() {
        viewModel.getGrocery()
    }

    override fun initObservers() {
        viewModel.groceryResult.observeResponse(
            viewLifecycleOwner,
            loading = {
                showLoadingDialog()
            },
            success = {
                hideLoadingDialog()
                Timber.d("GROCERIES ${it.data.size}")
                groceryGroupAdapter.setData(it.data)
            },
            empty = {
                hideLoadingDialog()
                Timber.d("EMPTY! DO SOMETHING")
            },

            error = {
                hideLoadingDialog()
            }
        )
    }

    private fun navigateToDetail(groupId: String) {
        findNavController().navigate(
            NoteFragmentDirections.actionNoteFragmentToNoteDetailFragment(
                groupId
            )
        )
    }

    private fun navigateToRecipeDetail(slug: String) {
        findNavController().navigate(
            NoteFragmentDirections.actionNoteFragmentToDetailRecipeFragment(
                slug
            )
        )
    }
}