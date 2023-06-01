package com.bangkit.snapcook.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentLoginBinding
import com.bangkit.snapcook.utils.extension.closeApp
import com.bangkit.snapcook.utils.extension.observeSingleEvent
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.showError
import com.bangkit.snapcook.utils.extension.showSnackBar
import com.bangkit.snapcook.utils.extension.showYesNoDialog
import org.koin.android.ext.android.inject

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showYesNoDialog(
                        title = getString(R.string.title_close_app),
                        message = getString(R.string.message_close_app),
                        onYes = {
                            closeApp()
                        }
                    )
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            btnLogin.popClick {
                loginUser()
            }

            btnRegister.popClick {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
        viewModel.loginResult.observeSingleEvent(
            viewLifecycleOwner,
            loading = {
                showLoadingDialog()
            },
            success = {
                hideLoadingDialog()
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            },
            error = {response ->
                hideLoadingDialog()
                binding.root.showSnackBar(response.errorMessage)
            },
        )
    }

    private fun loginUser(){
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        when {
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
                viewModel.loginUser(email, password)
            }
        }
    }




}