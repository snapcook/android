package com.bangkit.snapcook.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentSplashBinding
import com.bangkit.snapcook.utils.PreferenceManager
import com.bangkit.snapcook.utils.constant.AnimationConstants.SPLASH_ANIMATION
import com.bangkit.snapcook.utils.extension.slowShow
import org.koin.android.ext.android.inject

class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    private val pref: PreferenceManager by inject()

    private fun initLoading() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (!pref.isAlreadyOnBoard) {
                findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
                return@postDelayed
            }

            if (pref.isLogin) {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                return@postDelayed
            }

            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }, SPLASH_ANIMATION)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.imageView.slowShow()
    }

    override fun initProcess() {
        initLoading()
    }

    override fun initObservers() {
    }


}