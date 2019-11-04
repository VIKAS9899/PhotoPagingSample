package com.vik.photopagingsample.viewModels

import android.app.Application
import androidx.arch.core.util.Function
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.vik.photopagingsample.data.PhotoDataFactory
import com.vik.photopagingsample.models.Photo
import com.vik.photopagingsample.network.NetworkStatus
import java.util.concurrent.Executors

class MainViewModel(application: Application) : AndroidViewModel(application){

    private val executor = Executors.newFixedThreadPool(5)
    var networkLiveData: LiveData<NetworkStatus>
    private val pageSize = 10


    var photoLiveData:LiveData<PagedList<Photo>>

    init {
        val dataFactory = PhotoDataFactory()
        networkLiveData =
            Transformations.switchMap(dataFactory.dataSourceLiveData) { it.stateLiveData }

        val pagedConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .build()

        photoLiveData = LivePagedListBuilder(dataFactory, pagedConfig)
            .setFetchExecutor(executor)
            .build()

    }
}