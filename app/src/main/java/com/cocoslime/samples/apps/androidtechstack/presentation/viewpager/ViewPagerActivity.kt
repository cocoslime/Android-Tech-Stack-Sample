package com.cocoslime.samples.apps.androidtechstack.presentation.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cocoslime.samples.apps.androidtechstack.R
import com.cocoslime.samples.apps.androidtechstack.databinding.ActivityViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerActivity: FragmentActivity() {

    private lateinit var binding: ActivityViewPagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()
    }

    private fun initViewPager() {
        binding.pager.adapter = MainViewPagerAdapter(this)
        TabLayoutMediator(
            binding.tabLayout,
            binding.pager,
        ) { tab, position ->
            tab.text = getString(MainViewPagerAdapter.MAIN_FRAGMENT_CREATOR[position].titleResId)
        }.attach()
    }

    private class MainViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

        companion object {

            val MAIN_FRAGMENT_CREATOR = listOf(
                MainFragmentCreator(
                    { FirstFragment() },
                    R.string.tab_first
                ),
                MainFragmentCreator(
                    { SecondFragment() },
                    R.string.tab_second
                )
            )
        }

        override fun getItemCount(): Int = MAIN_FRAGMENT_CREATOR.size

        override fun createFragment(position: Int): Fragment {
            return MAIN_FRAGMENT_CREATOR[position].create()
        }

        data class MainFragmentCreator(
            val create: () -> Fragment,
            val titleResId: Int
        )
    }
}
