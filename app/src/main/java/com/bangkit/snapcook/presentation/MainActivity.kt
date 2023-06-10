package com.bangkit.snapcook.presentation

import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bangkit.snapcook.R
import com.bangkit.snapcook.base.BaseActivity
import com.bangkit.snapcook.databinding.ActivityMainBinding
import com.bangkit.snapcook.utils.extension.gone
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.show


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initUI() {
        val navHostBottomBar =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navControllerBottomBar = navHostBottomBar.navController
        binding.bottomNavigation.setupWithNavController(navControllerBottomBar)

        navControllerBottomBar.addOnDestinationChangedListener { _, currentDestination, _ ->
            if (isMainPage(currentDestination)) {
                binding.bottomAppBar.show()
                binding.fabCamera.show()
            } else {
                binding.bottomAppBar.gone()
                binding.fabCamera.gone()
            }
        }

        binding.fabCamera.popClick {
            navigateToFragment(navControllerBottomBar)
        }
    }

    private fun isMainPage(currentDestination: NavDestination): Boolean {
        return currentDestination.id == R.id.homeFragment
                || currentDestination.id == R.id.profileFragment
                || currentDestination.id == R.id.noteFragment
                || currentDestination.id == R.id.bookmarkFragment
    }

    private fun navigateToFragment(navController: NavController) {
        navController.navigate(R.id.detectIngredientFragment)
    }

    override fun initProcess() {}

    override fun initObservers() {}
}