package com.bangkit.snapcook.presentation.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseFragment
import com.bangkit.snapcook.data.model.User
import com.bangkit.snapcook.databinding.FragmentProfileBinding
import com.bangkit.snapcook.utils.PreferenceManager
import com.bangkit.snapcook.utils.extension.closeApp
import com.bangkit.snapcook.utils.extension.gone
import com.bangkit.snapcook.utils.extension.observeResponse
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.setImageUrl
import com.bangkit.snapcook.utils.extension.show
import com.bangkit.snapcook.utils.extension.showYesNoDialog
import com.bangkit.snapcook.utils.extension.slowShow
import com.bangkit.snapcook.utils.helper.extractData
import org.koin.android.ext.android.inject
import timber.log.Timber

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: ProfileViewModel by inject()
    private val pref: PreferenceManager by inject()

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
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            btnMyRecipe.popClick {
                findNavController().navigate(R.id.action_profileFragment_to_myRecipeFragment)
            }
            btnSetting.popClick {
                val test = "1 butir telur"
                Timber.d("TEST : ${test.extractData().first} - ${test.extractData().second}")
            }
            btnEditProfile.popClick {
                findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
            }
            btnLogout.popClick {
                showYesNoDialog(
                    title = getString(R.string.title_logout),
                    message = getString(R.string.message_logout),
                    onYes = {
                        pref.clearAllPreferences()
                        findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                    },
                )
            }
        }
    }

    override fun initProcess() {
        viewModel.getProfile()
    }

    @SuppressLint("SetTextI18n")
    override fun initObservers() {
        viewModel.profileResult.observeResponse(
            viewLifecycleOwner,
            loading = {
                showLoading(true)
            },
            success = {
                showLoading(false)
                val user: User = it.data

                binding.apply {
                    imgProfile.setImageUrl(user.photo)
                    tvUserName.text = user.name
                    tvUserSlug.text = "@${user.slug}"
                }

            },
            error = {
                showLoading(false)
            }
        )
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmeringLoadingProfile.startShimmer()
                shimmeringLoadingProfile.showShimmer(true)
                shimmeringLoadingProfile.show()
                layoutProfile.gone()
            } else {
                shimmeringLoadingProfile.stopShimmer()
                shimmeringLoadingProfile.showShimmer(false)
                shimmeringLoadingProfile.gone()
                layoutProfile.slowShow()
            }
        }
    }

}