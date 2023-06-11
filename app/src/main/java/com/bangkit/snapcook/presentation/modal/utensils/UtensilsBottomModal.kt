package com.bangkit.snapcook.presentation.modal.utensils

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.data.model.Utensil
import com.bangkit.snapcook.databinding.UtensilsSelectionBottomModalBinding
import com.bangkit.snapcook.utils.extension.popClick
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UtensilsBottomModal(
    private val defaultUtensil: List<Utensil>,
    private val utensils: List<Utensil>,
    private val onSave: (List<Utensil>) -> Unit) :
    BottomSheetDialogFragment() {

    private lateinit var binding: UtensilsSelectionBottomModalBinding
    private val adapter: UtensilsAdapter by lazy {
        UtensilsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = UtensilsSelectionBottomModalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rvUtensils.apply {
                adapter = this@UtensilsBottomModal.adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

            btnSave.popClick {
                onSave(adapter.retrieveSelectedUtensils())
                dismiss()
            }

        }
        adapter.setData(defaultUtensil)
        adapter.setSelectedData(utensils)
    }
}