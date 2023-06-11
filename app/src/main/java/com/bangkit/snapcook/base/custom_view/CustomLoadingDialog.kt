package com.bangkit.snapcook.base.custom_view

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.view.WindowManager
import com.bangkit.snapcook.R


internal class CustomLoadingDialog(context: Context): Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY)
        setCancelable(false)
        setContentView(R.layout.custom_loading_dialog_layout)
    }

    fun showDialog() {
        show()
    }

    fun dismissDialog() {
        dismiss()
    }
}