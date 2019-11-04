package com.vik.photopagingsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.vik.photopagingsample.adapters.PhotoPagedListAdapter
import com.vik.photopagingsample.network.Failed
import com.vik.photopagingsample.network.InitialLoading
import com.vik.photopagingsample.network.NetworkStatus
import com.vik.photopagingsample.network.RestApiFactory
import com.vik.photopagingsample.viewModels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mMainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMainViewModel=ViewModelProviders.of(this).get(MainViewModel::class.java)
        val adapter=PhotoPagedListAdapter()
        list.adapter=adapter
        list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        mMainViewModel.photoLiveData.observe(this, Observer { adapter.submitList(it) })

        mMainViewModel.networkLiveData.observe(this, Observer {
            adapter.setNetworkStatus(it)
            progressBar.visibility=if (it is InitialLoading) View.VISIBLE else View.GONE
            if (it is Failed){
                Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
            }
        })

    }
}
