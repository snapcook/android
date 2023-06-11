package com.bangkit.snapcook.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentRegisterBinding
import com.bangkit.snapcook.utils.extension.observeSingleEvent
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.setPopBackEnabled
import com.bangkit.snapcook.utils.extension.showError
import com.bangkit.snapcook.utils.extension.showSnackBar
import org.koin.android.ext.android.inject

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val viewModel: RegisterViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            toolBar.setPopBackEnabled()
            btnLogin.popClick {
                findNavController().popBackStack()
            }

            btnRegister.popClick {
                registerUser()
            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
        viewModel.registerResult.observeSingleEvent(
            viewLifecycleOwner,
            loading = {
                showLoadingDialog()
            },
            success = { response ->
                hideLoadingDialog()
                binding.root.showSnackBar(response.data)
                requireView().findNavController().popBackStack()
            },
            error = { response ->
                hideLoadingDialog()
                binding.root.showSnackBar(response.errorMessage)
            },
        )
    }

    private fun registerUser() {
        val fullName = binding.edtFullName.text.toString()
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        when {
            fullName.isEmpty() -> {
                binding.edtFullName.showError(getString(R.string.validation_must_not_empty))
            }
            email.isEmpty() -> {
                binding.edtEmail.showError(getString(R.string.validation_must_not_empty))
            }
            password.isEmpty() -> {
                binding.edtPassword.showError(getString(R.string.validation_must_not_empty))
            }
            password.length < 8 -> {
                binding.edtPassword.showError(getString(R.string.validation_password))
            }
            else -> {
                viewModel.registerUser(fullName, email, password)
            }
        }
    }
}