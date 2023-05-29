package com.bangkit.snapcook.presentation.take_ingredient_picture

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentTakeIngredientPictureBinding
import com.bangkit.snapcook.utils.enums.ImageSource
import com.bangkit.snapcook.utils.extension.getFileFromUri
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.setGlideImageUri
import com.bangkit.snapcook.utils.extension.setPopBackEnabled
import com.bangkit.snapcook.utils.extension.uriToBitmap
import com.bangkit.snapcook.utils.helper.detectIngredient
import java.io.File

class TakeIngredientPictureFragment : BaseFragment<FragmentTakeIngredientPictureBinding>() {

    private lateinit var viewModel: TakeIngredientPictureViewModel
    private var imageFile: File? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentTakeIngredientPictureBinding {
        return FragmentTakeIngredientPictureBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            toolBar.setPopBackEnabled()
            btnCamera.popClick {
                takePicture(ImageSource.CAMERA)
            }

            btnGallery.popClick {
                takePicture(ImageSource.GALLERY)
            }

            btnScan.popClick {

            }
        }
    }

    override fun onGalleryImageResult(uri: Uri?) {
        handleImagePicker(uri)
    }

    //karena proses image processing baru dilakukan pas tekan button scan disini hanya nampilin foto saja
    override fun onCameraImageResult(uri: Uri?, bitmap: Bitmap?) {
        handleImagePicker(uri)
    }

    private fun handleImagePicker(uri: Uri?) {
        imageFile = requireActivity().getFileFromUri(uri)
        val bitmap = uri?.uriToBitmap(requireContext())
//        val detectedBitmap = bitmap?.detectIngredient(requireContext())

        binding.imgIngredient.apply {
            scaleType = ImageView.ScaleType.CENTER
            setGlideImageUri(uri)
        }
    }


    override fun initProcess() {
    }

    override fun initObservers() {
    }

}