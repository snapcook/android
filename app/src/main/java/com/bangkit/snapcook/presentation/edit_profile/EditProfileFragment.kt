package com.bangkit.snapcook.presentation.edit_profile

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentEditProfileBinding
import com.bangkit.snapcook.utils.enums.ImageSource
import com.bangkit.snapcook.utils.extension.getFileFromUri
import com.bangkit.snapcook.utils.extension.observeResponse
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.setGlideImageUri
import com.bangkit.snapcook.utils.extension.setImageUrl
import com.bangkit.snapcook.utils.extension.showSnackBar
import org.koin.android.ext.android.inject
import java.io.File

class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>() {

    val viewModel: EditProfileViewModel by inject()
    private var imageFile: File? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentEditProfileBinding {
        return FragmentEditProfileBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            toolBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            imgProfile.popClick {
                takePicture(ImageSource.BOTH)
            }

            btnSave.popClick {
                viewModel.editProfile(
                    edtFullName.text.toString(),
                    imageFile,)
            }
        }
    }

    override fun onGalleryImageResult(uri: Uri?) {
        handleImagePicker(uri)
    }

    override fun onCameraImageResult(uri: Uri?, bitmap: Bitmap?) {
        handleImagePicker(uri)
    }

    private fun handleImagePicker(uri: Uri?) {
        imageFile = requireActivity().getFileFromUri(uri)
        binding.imgProfile.apply {
            setGlideImageUri(uri)
        }
    }

    override fun initProcess() {
        viewModel.getProfile()
    }

    override fun initObservers() {
        viewModel.profileResult.observeResponse(
            viewLifecycleOwner,
            success = {
                hideLoadingDialog()
                binding.apply {
                    imgProfile.setImageUrl(it.data.photo)
                    edtFullName.setText(it.data.name)
                }
            },
            loading = {
                showLoadingDialog()
            },
            error = {
                hideLoadingDialog()
            },
        )

        viewModel.profileEditResult.observeResponse(
            viewLifecycleOwner,
            success = {
                hideLoadingDialog()
                findNavController().popBackStack()
            },
            loading = {
                showLoadingDialog()
            },
            error = {
                hideLoadingDialog()
                binding.root.showSnackBar(it.errorMessage)
            },
        )
    }

}