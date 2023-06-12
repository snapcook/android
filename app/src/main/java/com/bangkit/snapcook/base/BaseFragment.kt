package com.bangkit.snapcook.base

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.custom_view.CustomLoadingDialog
import com.bangkit.snapcook.utils.enums.ImageSource
import com.bangkit.snapcook.utils.extension.checkIfImageTooSmall
import com.bangkit.snapcook.utils.extension.getImageUri
import com.bangkit.snapcook.utils.extension.showOKDialog
import com.bangkit.snapcook.utils.extension.showSnackBar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!
    private lateinit var loadingDialog: CustomLoadingDialog

    private var allowSmallPicture = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container, savedInstanceState)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = CustomLoadingDialog(requireContext())
        initIntent()
        initUI()
        initActions()
        initProcess()
        initObservers()
    }

    abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): VB

    abstract fun initUI()

    abstract fun initProcess()

    abstract fun initObservers()

    protected open fun initIntent() {}

    protected open fun initActions() {}
    protected fun takePicture(source: ImageSource) {
        Dexter.withContext(requireActivity())
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    if (source == ImageSource.BOTH){
                        showImagePickerMenu()
                        return
                    }

                    if (source == ImageSource.CAMERA){
                        takePictureRegistration.launch()
                        return
                    }

                    if (source == ImageSource.GALLERY){
                        pickFileImage.launch("image/*")
                        allowSmallPicture = true
                        return
                    }

                    if (source == ImageSource.GALLERY_DETECTION){
                        pickFileImage.launch("image/*")
                        allowSmallPicture = false
                        return
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }
            }).withErrorListener {
                binding.root.showSnackBar(getString(R.string.warning_image_picker_permission))
            }.onSameThread()
            .check()
    }

    protected open fun onCameraImageResult(uri: Uri?, bitmap: Bitmap?) {}
    protected open fun onGalleryImageResult(uri: Uri?) {}

    open fun showLoadingDialog() {
        loadingDialog.showDialog()
    }
    open fun hideLoadingDialog() {
        loadingDialog.dismissDialog()
    }

    private fun showImagePickerMenu() {
        AlertDialog.Builder(requireActivity())
            .setTitle(context?.getString(R.string.label_choose_image_picker_method))
            .setItems(R.array.pictures) { _, p1 ->
                if (p1 == 0) {
                    takePictureRegistration.launch()
                } else {
                    pickFileImage.launch("image/*")
                }
            }.create().show()
    }

    private val takePictureRegistration =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                val uri = requireActivity().getImageUri(bitmap)
                onCameraImageResult(uri, bitmap)
            }
        }

    private val pickFileImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                if (!allowSmallPicture) {
                    if (requireActivity().checkIfImageTooSmall(uri)) {
                        showOKDialog(
                            title = getString(R.string.title_warning),
                            message = getString(R.string.warning_image_too_small),
                        )
                    } else {
                        onGalleryImageResult(uri)
                    }
                    return@registerForActivityResult
                }

                onGalleryImageResult(uri)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
