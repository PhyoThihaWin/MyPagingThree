package com.pthw.mypagingthree.feature.firestorechat.activity.photoview

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: FragmentActivity, fragmentList: ArrayList<Fragment>) :
    FragmentStateAdapter(fragment) {
    private val list: ArrayList<Fragment>

    init {
        this.list = fragmentList
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }

}