package com.bangkit.snapcook.base.custom_view

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.bangkit.snapcook.R


internal class CustomLoadingDialog(context: Context): Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        setContentView(R.layout.custom_loading_dialog_layout)
        setCancelable(false)
    }

    fun showDialog() {
        show()
    }

    fun dismissDialog() {
        dismiss()
    }
}