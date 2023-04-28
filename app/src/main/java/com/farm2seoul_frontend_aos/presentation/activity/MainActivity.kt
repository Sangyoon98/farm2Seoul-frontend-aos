package com.farm2seoul_frontend_aos.presentation.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.farm2seoul_frontend_aos.presentation.adapter.FragmentViewPagerAdapter
import com.farm2seoul_frontend_aos.R
import com.farm2seoul_frontend_aos.databinding.ActivityMainBinding
import com.farm2seoul_frontend_aos.presentation.fragment.DailyAuction
import com.farm2seoul_frontend_aos.presentation.fragment.Information
import com.farm2seoul_frontend_aos.presentation.fragment.Favorites
import com.farm2seoul_frontend_aos.presentation.viewmodel.MainActivityViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!
    private lateinit var viewPagerAdapter: FragmentViewPagerAdapter
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private var dailyAuction: DailyAuction? = null
    private var fragment2: Information? = null
    private var fragment3: Favorites? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragmentManage()

        binding.searchButton.setOnClickListener {
            mainActivityViewModel.search(binding.editText.text.toString())
            binding.fragmentLayout.currentItem = 0
        }
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }

    private fun fragmentManage() {
        val fragmentList = listOf(DailyAuction(), Information(), Favorites())

        /** ViewPager2 Fragment code */

        val tabName = listOf("일별경매", "정보 마당", "즐겨찾기")
        val tabIcon = listOf(R.drawable.daily_auction, R.drawable.store_info, R.drawable.star)

        viewPagerAdapter = FragmentViewPagerAdapter(this)
        viewPagerAdapter.fragments.addAll(fragmentList)
        binding.fragmentLayout.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.fragmentLayout) {tab, pos ->
            tab.text = tabName[pos]
            tab.setIcon(tabIcon[pos])
        }.attach()


        /*fragment1 = Fragment1()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_layout, fragment1!!).commit()*/

        //replaceView(fragmentList[0])

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                //binding.scrollLayout.fullScroll(NestedScrollView.FOCUS_UP)
                /*when (tab?.position) {
                    0 -> replaceView(fragmentList[0])
                    1 -> replaceView(fragmentList[1])
                    2 -> replaceView(fragmentList[2])
                }*/
                /*when (tab?.position) {
                    0 -> {
                        if (fragment1 == null) {
                            fragment1 = Fragment1()
                            supportFragmentManager.beginTransaction().add(R.id.fragment_layout, fragment1!!).commit()
                        }
                        if (fragment1 != null) supportFragmentManager.beginTransaction().show(fragment1!!).commit()
                        if (fragment2 != null) supportFragmentManager.beginTransaction().hide(fragment2!!).commit()
                        if (fragment3 != null) supportFragmentManager.beginTransaction().hide(fragment3!!).commit()
                    }
                    1 -> {
                        if (fragment2 == null) {
                            fragment2 = Fragment2()
                            supportFragmentManager.beginTransaction().add(R.id.fragment_layout, fragment2!!).commit()
                        }
                        if (fragment1 != null) supportFragmentManager.beginTransaction().hide(fragment1!!).commit()
                        if (fragment2 != null) supportFragmentManager.beginTransaction().show(fragment2!!).commit()
                        if (fragment3 != null) supportFragmentManager.beginTransaction().hide(fragment3!!).commit()
                    }
                    2 -> {
                        if (fragment3 == null) {
                            fragment3 = Fragment3()
                            supportFragmentManager.beginTransaction().add(R.id.fragment_layout, fragment3!!).commit()
                        }
                        if (fragment1 != null) supportFragmentManager.beginTransaction().hide(fragment1!!).commit()
                        if (fragment2 != null) supportFragmentManager.beginTransaction().hide(fragment2!!).commit()
                        if (fragment3 != null) supportFragmentManager.beginTransaction().show(fragment3!!).commit()
                    }
                }*/
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //binding.scrollLayout.fullScroll(NestedScrollView.FOCUS_UP)
            }
        })
    }

    private fun replaceView(tab: Fragment) {
        var selectedFragment: Fragment? = null
        selectedFragment = tab
        selectedFragment?.let {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_layout, it).commit()
        }
    }

    // Fragment 새로고침
    fun refreshFragment(fragment: Fragment) {
        viewPagerAdapter.refreshFragment(0, fragment)
    }
}