package com.bangkit.snapcook.presentation.add_recipe

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.data.model.Utensil
import com.bangkit.snapcook.databinding.FragmentAddRecipeBinding
import com.bangkit.snapcook.presentation.add_recipe.adapter.AddIngredientAdapter
import com.bangkit.snapcook.presentation.add_recipe.adapter.AddSpiceAdapter
import com.bangkit.snapcook.presentation.add_recipe.adapter.AddStepAdapter
import com.bangkit.snapcook.presentation.add_recipe.adapter.AddUtensilsAdapter
import com.bangkit.snapcook.presentation.add_recipe.adapter.SelectCategoryAdapter
import com.bangkit.snapcook.presentation.detail.DetailRecipeFragmentArgs
import com.bangkit.snapcook.presentation.modal.utensils.UtensilsBottomModal
import com.bangkit.snapcook.utils.enums.ImageSource
import com.bangkit.snapcook.utils.extension.extractToMinutes
import com.bangkit.snapcook.utils.extension.getFileFromUri
import com.bangkit.snapcook.utils.extension.observeResponse
import com.bangkit.snapcook.utils.extension.observeSingleEvent
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.setGlideImageUri
import com.bangkit.snapcook.utils.extension.setImageUrl
import com.bangkit.snapcook.utils.extension.showOKDialog
import com.bangkit.snapcook.utils.extension.showSnackBar
import org.koin.android.ext.android.inject
import java.io.File

class AddRecipeFragment : BaseFragment<FragmentAddRecipeBinding>() {

    private val viewModel: AddRecipeViewModel by inject()

    private var imageFile: File? = null
    private var mUri: Uri? = null
    private val selectedUtensils = ArrayList<Utensil>()
    private val utensils = ArrayList<Utensil>()
    private var slug: String = ""
    private var recipeId: String? = null

    private val addIngredientAdapter: AddIngredientAdapter by lazy {
        AddIngredientAdapter()
    }

    private val addSpiceAdapter: AddSpiceAdapter by lazy {
        AddSpiceAdapter()
    }

    private val addStepAdapter: AddStepAdapter by lazy {
        AddStepAdapter(binding.rvSteps)
    }

    private val addUtensilAdapter: AddUtensilsAdapter by lazy {
        AddUtensilsAdapter()
    }

    private val categoryAdapter: SelectCategoryAdapter by lazy {
        SelectCategoryAdapter()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentAddRecipeBinding {
        return FragmentAddRecipeBinding.inflate(inflater, container, false)
    }

    override fun initIntent() {
        val safeArgs = arguments?.let { DetailRecipeFragmentArgs.fromBundle(it) }
        slug = safeArgs?.slug ?: ""
    }

    override fun initUI() {
        binding.apply {
            mUri?.let {
                imgFood.setImageURI(it)
            }

            btnBack.popClick {
                findNavController().popBackStack()
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

            rvCookingWare.apply {
                adapter = addUtensilAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }

            rvCategory.apply {
                adapter = categoryAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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

            btnAddCookingWare.popClick {
                val bottomSheetDialogFragment = UtensilsBottomModal(utensils, selectedUtensils) {
                    selectedUtensils.clear()
                    selectedUtensils.addAll(it)
                    addUtensilAdapter.setData(selectedUtensils)
                }
                bottomSheetDialogFragment.show(childFragmentManager, "UtensilsBottomModalFragment")
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
        binding.imgFood.apply {
            scaleType = ImageView.ScaleType.CENTER
            setGlideImageUri(uri)
        }
    }

    private fun uploadRecipe() {
        binding.apply {
            val recipeName = edtRecipeName.text.toString()
            val recipeDescription = edtRecipeDescription.text.toString()
            val selectedCategory = categoryAdapter.retrieveSelectedId()
            val portion = edtPortion.text.toString()
            val estimateTime = edtCookingTime.text.toString().extractToMinutes()
            val ingredients = addIngredientAdapter.retrieveResult()
            val spices = addSpiceAdapter.retrieveResult()
            val steps = addStepAdapter.retrieveResult()
            val utensils = selectedUtensils

            if (slug == ""){
                if (imageFile == null) {
                    root.showSnackBar(getString(R.string.validation_photo))
                }
            }
            when {
                recipeName.isEmpty() -> {
                    edtRecipeDescription.error = getString(R.string.validation_must_not_empty)
                }

                portion.isEmpty() -> {
                    edtPortion.error = getString(R.string.validation_must_not_empty)
                }

                estimateTime == null -> {
                    edtPortion.error = getString(R.string.validation_estimate_time_must_valid)
                }

                selectedCategory.isEmpty() -> {
                    root.showSnackBar(getString(R.string.validation_not_selected_category))
                }

                ingredients.isEmpty() -> {
                    root.showSnackBar(getString(R.string.validation_ingredient_empty))
                }

                spices.isEmpty() -> {
                    root.showSnackBar(getString(R.string.validation_spices_empty))
                }

                steps.isEmpty() -> {
                    root.showSnackBar(getString(R.string.validation_steps_empty))
                }

                utensils.isEmpty() -> {
                    root.showSnackBar(getString(R.string.validation_utensils_empty))
                }

                else -> {
                    viewModel.uploadRecipe(
                        photo = imageFile,
                        title = recipeName,
                        description = recipeDescription,
                        mainCategory = "Makanan",
                        secondCategoryId = selectedCategory,
                        totalServing = portion,
                        estimatedTime = estimateTime.toString(),
                        mainIngredients = ingredients,
                        spices = spices,
                        steps = steps,
                        utensils = utensils,
                        recipeId = recipeId,
                        isEdit = slug.isNotEmpty()
                    )
                }
            }
        }
    }

    override fun initProcess() {
        viewModel.getCategories()
        viewModel.getUtensils()
        if (slug.isNotEmpty()) {
            viewModel.getRecipeDetail(slug)
        }

    }

    @SuppressLint("SetTextI18n")
    override fun initObservers() {
        viewModel.uploadResult.observeSingleEvent(
            viewLifecycleOwner,
            loading = {
                showLoadingDialog()
            },
            success = {
                hideLoadingDialog()
                showOKDialog(
                    title = "Berhasil upload resep",
                    message = "tekan OK untuk kembali",
                    onYes = {
                        findNavController().popBackStack()
                    },
                )
            },
            error = { response ->
                hideLoadingDialog()
                binding.root.showSnackBar(response.errorMessage)
            },
        )

        viewModel.categoryResult.observeResponse(
            viewLifecycleOwner,
            loading = {
                showLoadingDialog()
            },
            success = {
                hideLoadingDialog()
                categoryAdapter.setData(it.data)
                if(slug.isNotEmpty()){
                    observeDetailResult()
                }
            },
            error = { response ->
                hideLoadingDialog()
            },
        )

        viewModel.utensilResult.observeResponse(
            viewLifecycleOwner,
            loading = {},
            success = {
                utensils.clear()
                utensils.addAll(it.data)
            },
            error = {},
        )
    }

    private fun observeDetailResult(){
        viewModel.recipeDetailResult.observeResponse(
            viewLifecycleOwner,
            loading = {
                showLoadingDialog()
            },
            success = {
                hideLoadingDialog()
                val recipe = it.data
                recipeId = recipe.id
                binding.apply {
                    edtRecipeName.setText(recipe.title)
                    edtRecipeDescription.setText(recipe.description)
                    edtPortion.setText(recipe.totalServing.toString())
                    edtCookingTime.setText("${recipe.estimatedTime} menit")
                    addIngredientAdapter.setData(recipe.fullIngredients)
                    addUtensilAdapter.setData(recipe.utensils)
                    addSpiceAdapter.setData(recipe.spices)
                    addStepAdapter.setData(recipe.steps)
                    categoryAdapter.setSelectedData(recipe.secondCategoryId)
                    imgFood.setImageUrl(recipe.photo)
                    selectedUtensils.clear()
                    selectedUtensils.addAll(recipe.utensils)
                }
            },
            error = {
                hideLoadingDialog()
            },
        )
    }
}