package com.bangkit.snapcook.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentRegisterBinding
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.setPopBackEnabled
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
            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }
}