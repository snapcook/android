package com.bangkit.snapcook.presentation.cooking.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkit.snapcook.presentation.cooking.CookingContentFragment

class CookingPagerAdapter(
    fa: FragmentActivity,
    private val steps: List<String>,
    private val ingredients: List<String>,
    private val spices: List<String>,
) :
    FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = steps.size

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putString("step", steps[position])
        bundle.putStringArray("ingredients", ingredients.toTypedArray())
        bundle.putStringArray("spices", spices.toTypedArray())
        val fragment = CookingContentFragment()
        fragment.arguments = bundle
        return fragment
    }

}