package com.bangkit.snapcook.utils.extension

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.bangkit.snapcook.R
import com.bangkit.snapcook.data.network.ApiResponse
import kotlin.system.exitProcess

fun Fragment.showOKDialog(title: String, message: String, onYes: () -> Unit) {
    AlertDialog.Builder(requireActivity()).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton("OK") { p0, _ ->
            p0.dismiss()
        }
        setOnDismissListener {
            onYes.invoke()
        }
    }.create().show()
}

fun Fragment.showYesNoDialog(title: String, message: String, onYes: () -> Unit) {
    AlertDialog.Builder(requireContext()).apply {
        setTitle(title)
        setMessage(message)
        setNegativeButton(getString(R.string.label_no)) { p0, _ ->
            p0.dismiss()
        }
        setPositiveButton(getString(R.string.label_yes)) { _, _ ->
            onYes.invoke()
        }
    }.create().show()
}

fun Fragment.closeApp(){
    requireActivity().finish()
    exitProcess(0)
}

fun <T> LiveData<ApiResponse<T>>.observeResponse(
    lifeCycleOwner: LifecycleOwner,
    loading: () -> Unit,
    success: (ApiResponse.Success<T>) -> Unit,
    error: (ApiResponse.Error) -> Unit,
    empty: (() -> Unit)? = null
) {
    this.observe(lifeCycleOwner) { result ->
        when (result) {
            is ApiResponse.Loading -> loading.invoke()
            is ApiResponse.Success -> success.invoke(result)
            is ApiResponse.Error -> error.invoke(result)
            is ApiResponse.Empty -> empty?.invoke()
        }
    }
}