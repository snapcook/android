package com.bangkit.snapcook.presentation.modal.prediction

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.databinding.PredictionBottomModalBinding
import com.bangkit.snapcook.utils.extension.popClick
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PredictionBottomModal(
    private val results: List<String>,
    private val onSave: (List<String>) -> Unit,
) : BottomSheetDialogFragment() {

    private lateinit var binding: PredictionBottomModalBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private val adapter: PredictionAdapter by lazy {
        PredictionAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = PredictionBottomModalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        bottomSheetBehavior = BottomSheetBehavior.from(view)
//        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED;

        binding.apply {
            rvResult.apply {
                adapter = this@PredictionBottomModal.adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            btnSave.popClick {
                onSave(adapter.retrieveData())
                dismiss()
            }
            btnAdd.popClick {
                if (edtIngredient.text.toString().isEmpty()) return@popClick
                adapter.addData(edtIngredient.text.toString())
            }
        }
        adapter.setData(results)
    }
}