package com.bangkit.snapcook.presentation.detect_ingredient

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentDetectIngredientBinding
import com.bangkit.snapcook.presentation.modal.prediction.PredictionAdapter
import com.bangkit.snapcook.utils.enums.ImageSource
import com.bangkit.snapcook.utils.extension.getFileFromUri
import com.bangkit.snapcook.utils.extension.gone
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.setGlideImageUri
import com.bangkit.snapcook.utils.extension.setPopBackEnabled
import com.bangkit.snapcook.utils.extension.show
import com.bangkit.snapcook.utils.extension.uriToBitmap
import com.bangkit.snapcook.utils.helper.detectIngredient
import org.koin.android.ext.android.inject
import java.io.File

class DetectIngredientFragment : BaseFragment<FragmentDetectIngredientBinding>() {

    private val viewModel: DetectIngredientViewModel by inject()
    private val predictionAdapter: PredictionAdapter by lazy {
        PredictionAdapter()
    }
    private var imageFile: File? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentDetectIngredientBinding {
        return FragmentDetectIngredientBinding.inflate(inflater, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initUI() {
        binding.apply {
            toolBar.setPopBackEnabled()
            btnCamera.popClick {
                takePicture(ImageSource.CAMERA)
            }

            btnGallery.popClick {
                takePicture(ImageSource.GALLERY)
            }

            btnReset.popClick {
                hidePredictionLayout()
            }

            rvResult.apply {
                adapter = predictionAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            btnFindRecipe.popClick {
                navigateToResultPage()
            }
            btnAdd.popClick {
                if (edtIngredient.text.toString().isEmpty()) return@popClick
                predictionAdapter.addData(edtIngredient.text.toString())
            }

            btnDrag.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    var height = 0
                    when (event?.actionMasked) {
                        MotionEvent.ACTION_DOWN -> {}
                        MotionEvent.ACTION_MOVE -> {
                            height = binding.root.height
                            val guideLine = binding.guideline
                            val params = guideLine.layoutParams as ConstraintLayout.LayoutParams
                            val percent = event.rawY / height
                            if (percent < 0.6 && percent > 0.2) {
                                params.guidePercent = percent
                                guideLine.layoutParams = params
                            }
                        }
                        else -> return false
                    }
                    return true
                }
            })
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
        val bitmap = uri?.uriToBitmap(requireContext())
        val detectedBitmap = bitmap?.detectIngredient(requireContext()) {
            predictionAdapter.setData(it)
        }

        binding.imgIngredient.apply {
            scaleType = ImageView.ScaleType.CENTER
            setGlideImageUri(uri)
        }

        binding.apply {
            imgResult.apply {
                scaleType = ImageView.ScaleType.FIT_XY
                setImageBitmap(detectedBitmap)
            }
            val params = binding.guideline.layoutParams as ConstraintLayout.LayoutParams
            params.guidePercent = 0.65f
            guideline.layoutParams = params
            showPredictionLayout()
        }
    }

    private fun hidePredictionLayout() {
        binding.apply {
            imgResult.gone()
            layoutResult.gone()
            card.show()
        }
    }

    private fun showPredictionLayout() {
        binding.apply {
            imgResult.show()
            layoutResult.show()
            card.gone()
        }
    }

    private fun navigateToResultPage(){
        findNavController().navigate(
            DetectIngredientFragmentDirections.actionDetectIngredientFragmentToRecommendedFragment(
                predictionAdapter.retrieveData().toTypedArray()
            )
        )
    }

    override fun initProcess() {}

    override fun initObservers() {}


}