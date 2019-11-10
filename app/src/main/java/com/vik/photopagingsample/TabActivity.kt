package com.vik.photopagingsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vik.photopagingsample.adapters.TabFragmentAdapter
import kotlinx.android.synthetic.main.activity_tab.*

class TabActivity :AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

        viewPager.adapter = TabFragmentAdapter(supportFragmentManager, 20)
        tabLayout.setupWithViewPager(viewPager)
    }
}