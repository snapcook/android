package com.bangkit.snapcook.presentation.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.databinding.FragmentOnBoardingBinding
import com.bangkit.snapcook.presentation.onboard.adapter.ViewPagerFragmentAdapter
import com.bangkit.snapcook.presentation.onboard.content.FirstOnBoardContentFragment
import com.bangkit.snapcook.presentation.onboard.content.SecondOnboardContentFragment
import com.bangkit.snapcook.presentation.onboard.content.ThirdOnboardContentFragment
import com.bangkit.snapcook.utils.PreferenceManager
import com.bangkit.snapcook.utils.extension.gone
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.show
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject


class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>() {
    private val pref: PreferenceManager by inject()

    private val fragmentList = ArrayList<Fragment>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentOnBoardingBinding {
        return FragmentOnBoardingBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        fragmentList.add(FirstOnBoardContentFragment())
        fragmentList.add(SecondOnboardContentFragment())
        fragmentList.add(ThirdOnboardContentFragment())

        binding.apply {
            viewPager.adapter = ViewPagerFragmentAdapter(requireActivity(), fragmentList)
            viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    checkCurrentItem(position)
                }
            })

            TabLayoutMediator(tlIntro, viewPager)
            { tab, position -> }.attach()

            btnNext.popClick {
                if (btnNext.text == getString(R.string.action_get_started)){
                    navigateToLogin()
                }

                viewPager.apply {
                    beginFakeDrag()
                    fakeDragBy(-10f)
                    endFakeDrag()
                }
            }

            btnPrev.popClick {
                binding.viewPager.apply {
                    beginFakeDrag()
                    fakeDragBy(10f)
                    endFakeDrag()
                }
            }

            btnSkip.popClick {
                navigateToLogin()
            }

        }
    }

    private fun navigateToLogin() {
        pref.markUserDoneOnboard()
        findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)
    }

    private fun checkCurrentItem(currentItem: Int) {
        binding.apply {
            if (currentItem == 0) {
                btnSkip.show()
                btnPrev.gone()
                btnNext.text = getString(R.string.action_next)
            }

            if (currentItem == 1) {
                btnSkip.show()
                btnPrev.show()
                btnNext.text = getString(R.string.action_next)
            }

            if (currentItem == 2) {
                btnSkip.gone()
                btnPrev.show()
                btnNext.text = getString(R.string.action_get_started)
            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {

    }

}