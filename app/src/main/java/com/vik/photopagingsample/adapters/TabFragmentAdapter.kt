package com.vik.photopagingsample.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.vik.photopagingsample.fragments.TabFragment

class TabFragmentAdapter(fragmentManager: FragmentManager,tabCount:Int) : FragmentPagerAdapter(fragmentManager){

    private val tabCount = tabCount

    override fun getItem(position: Int): Fragment {
        val bundle=Bundle().apply { putInt("pageNumber",position+1) }
        return TabFragment().apply { arguments=bundle }
    }

    override fun getCount(): Int =tabCount

    override fun getPageTitle(position: Int): CharSequence? ="Title ${position+1}"

}