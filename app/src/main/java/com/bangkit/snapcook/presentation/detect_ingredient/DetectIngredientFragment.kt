package com.bangkit.snapcook.presentation.detect_ingredient

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentDetectIngredientBinding
import com.bangkit.snapcook.utils.enums.ImageSource
import com.bangkit.snapcook.utils.extension.getFileFromUri
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.setGlideImageUri
import com.bangkit.snapcook.utils.extension.setPopBackEnabled
import org.koin.android.ext.android.inject
import java.io.File


class DetectIngredientFragment : BaseFragment<FragmentDetectIngredientBinding>() {

    private val viewModel: DetectIngredientViewModel by inject()

    private var imageFile: File? = null


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetectIngredientBinding {
        return FragmentDetectIngredientBinding.inflate(inflater, container, false)
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