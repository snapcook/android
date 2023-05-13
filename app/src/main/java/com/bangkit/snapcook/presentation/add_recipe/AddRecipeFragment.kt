package com.bangkit.snapcook.presentation.add_recipe

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentAddRecipeBinding
import com.bangkit.snapcook.presentation.add_recipe.adapter.AddIngredientAdapter
import com.bangkit.snapcook.presentation.add_recipe.adapter.AddSpiceAdapter
import com.bangkit.snapcook.presentation.add_recipe.adapter.AddStepAdapter
import com.bangkit.snapcook.utils.extension.getFileFromUri
import com.bangkit.snapcook.utils.extension.getImageUri
import com.bangkit.snapcook.utils.extension.popClick
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
        savedInstanceState: Bundle?
    ): FragmentAddRecipeBinding {
        return FragmentAddRecipeBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            mUri?.let {
                imgFood.setImageURI(it)
            }

            btnAddPicture.popClick {
                takePicture()
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
                val result = addIngredientAdapter.retrieveResult()
                val resultStep = addStepAdapter.retrieveResult()

                Timber.d("RESULT $result")

                Timber.d("RESULT STEPS $resultStep")
                tvTest.text = result
            }
        }

        addIngredientAdapter.itemTouchHelper.attachToRecyclerView(binding.rvIngredient)
        addSpiceAdapter.itemTouchHelper.attachToRecyclerView(binding.rvSpices)
        addStepAdapter.itemTouchHelper.attachToRecyclerView(binding.rvSteps)
    }

    override fun takePictureRegistration() {
        takePictureRegistration.launch()
    }

    override fun pickFileRegistration() {
        pickFileImage.launch("image/*")
    }

    private val takePictureRegistration =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                val uri = requireActivity().getImageUri(bitmap)
                mUri = uri
                imageFile = requireActivity().getFileFromUri(uri)
                binding.imgFood.apply {
                    setImageBitmap(bitmap)
                    scaleType = ImageView.ScaleType.CENTER
                }
            }
        }

    private val pickFileImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                mUri = uri
                imageFile = requireActivity().getFileFromUri(uri)
                binding.imgFood.apply {
                    setImageURI(uri)
                    scaleType = ImageView.ScaleType.CENTER
                }
            }
        }

    override fun initProcess() {
    }

    override fun initObservers() {
    }
}