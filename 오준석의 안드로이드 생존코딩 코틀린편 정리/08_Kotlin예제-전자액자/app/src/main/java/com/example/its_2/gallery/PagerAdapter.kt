package com.example.its_2.gallery

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class PagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    // ViewPager가 표시할 Fragment 목록
    private val items = ArrayList<Fragment>()

    // position 위치의 Fragment
    override fun getItem(position: Int): Fragment {
        return items[position]
    }

    // 아이템 갯수
    override fun getCount(): Int {
        return items.size
    }

    fun updateFragments(items: List<Fragment>) {
        this.items.addAll(items)
    }
}