package com.bangkit.snapcook.presentation.add_recipe

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentAddRecipeBinding
import com.bangkit.snapcook.presentation.add_recipe.adapter.AddIngredientAdapter
import com.bangkit.snapcook.presentation.add_recipe.adapter.AddSpiceAdapter
import com.bangkit.snapcook.presentation.add_recipe.adapter.AddStepAdapter
import com.bangkit.snapcook.utils.enums.ImageSource
import com.bangkit.snapcook.utils.extension.extractToMinutes
import com.bangkit.snapcook.utils.extension.getFileFromUri
import com.bangkit.snapcook.utils.extension.observeSingleEvent
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.setGlideImageUri
import com.bangkit.snapcook.utils.extension.showSnackBar
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.io.File

class AddRecipeFragment : BaseFragment<FragmentAddRecipeBinding>() {

    private val viewModel: AddRecipeViewModel by inject()

    private var imageFile: File? = null
    private var mUri: Uri? = null

    private val addIngredientAdapter: AddIngredientAdapter by lazy {
        AddIngredientAdapter()
    }

    private val addSpiceAdapter: AddSpiceAdapter by lazy {
        AddSpiceAdapter()
    }

    private val addStepAdapter: AddStepAdapter by lazy {
        AddStepAdapter(binding.rvSteps)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentAddRecipeBinding {
        return FragmentAddRecipeBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            mUri?.let {
                imgFood.setImageURI(it)
            }

            btnAddPicture.popClick {
                takePicture(ImageSource.GALLERY)
            }

            rvIngredient.apply {
                adapter = addIngredientAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                isNestedScrollingEnabled = false
            }

            rvSpices.apply {
                adapter = addSpiceAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                isNestedScrollingEnabled = false
            }

            rvSteps.apply {
                adapter = addStepAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                isNestedScrollingEnabled = false
            }

            btnAddMainIngredient.popClick {
                addIngredientAdapter.addData()
            }

            btnAddSpice.popClick {
                addSpiceAdapter.addData()
            }

            btnAddStep.popClick {
                addStepAdapter.addData()
            }

            btnSave.popClick {
                uploadRecipe()
            }
        }

        addIngredientAdapter.itemTouchHelper.attachToRecyclerView(binding.rvIngredient)
        addSpiceAdapter.itemTouchHelper.attachToRecyclerView(binding.rvSpices)
        addStepAdapter.itemTouchHelper.attachToRecyclerView(binding.rvSteps)
    }

    override fun onGalleryImageResult(uri: Uri?) {
        handleImagePicker(uri)
    }

    private fun handleImagePicker(uri: Uri?) {
        imageFile = requireActivity().getFileFromUri(uri)
        Timber.d("FILE PATH IS ${imageFile?.absolutePath}")
        Timber.d("FILE EXTENSION IS ${imageFile?.extension}")

        binding.imgFood.apply {
            scaleType = ImageView.ScaleType.CENTER
            setGlideImageUri(uri)
        }
    }

    private fun uploadRecipe() {
        binding.apply {
            val recipeName = edtRecipeName.text.toString()
            val recipeDescription = edtRecipeDescription.text.toString()
            val portion = edtPortion.text.toString()
            val estimateTime = edtCookingTime.text.toString().extractToMinutes().toString()
            val ingredients = addIngredientAdapter.retrieveResult()
            val spices = addSpiceAdapter.retrieveResult()
            val steps = addStepAdapter.retrieveResult()
            val utensils = listOf("Garpu", "Wajan", "Mangkuk")

            viewModel.uploadRecipe(
                photo = imageFile!!,
                title = recipeName,
                description = recipeDescription,
                mainCategory = "Makanan",
                totalServing = portion,
                estimatedTime = estimateTime,
                mainIngredients = ingredients,
                spices = spices,
                steps = steps,
                utensils = utensils
            )
            return
//            when {
//                storyDescription.isEmpty() -> {
//                    edtDescription.showError(getString(R.string.validation_must_not_empty))
//                }
//                imageFile == null -> {
//                    root.showSnackBar(getString(R.string.validation_photo))
//                }
//                else -> {
//                    viewModel.uploadRecipe(
//                        photo = imageFile!!,
//                        description = storyDescription,
//                        latitude = if (allowLocation) myLocation?.latitude?.toFloat() else null,
//                        longitude = if (allowLocation) myLocation?.longitude?.toFloat() else null
//                    )
//                }
//            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
        viewModel.uploadResult.observeSingleEvent(
            viewLifecycleOwner,
            loading = {
                showLoadingDialog()
            },
            success = {
                hideLoadingDialog()
            },
            error = { response ->
                hideLoadingDialog()
                binding.root.showSnackBar(response.errorMessage)
            },
        )
    }
}