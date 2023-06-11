package com.bangkit.snapcook.utils.extension

import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController

fun Toolbar.setPopBackEnabled(){
    setNavigationOnClickListener {
        it.findNavController().popBackStack()
    }
}