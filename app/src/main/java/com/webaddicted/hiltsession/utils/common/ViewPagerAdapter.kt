package com.webaddicted.hiltsession.utils.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.*

/**
* Created by Deepak Sharma on 12-Sep-2021.
*/
class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
      FragmentStateAdapter(fragmentActivity) {
      private val mFragmentList: MutableList<Fragment> = ArrayList()
      private val mFragmentTitleList: MutableList<String> =
        ArrayList()


      override fun createFragment(position: Int): Fragment {
            return mFragmentList[position]
          }

      override fun getItemCount(): Int {
            return mFragmentList.size
          }
      fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
          }
}