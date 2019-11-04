package com.vik.photopagingsample.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.vik.photopagingsample.models.Photo

class PhotoDataFactory :DataSource.Factory<Int,Photo>(){

    var dataSourceLiveData:MutableLiveData<PhotoDataSource> = MutableLiveData()

    private lateinit var photoDataSource: PhotoDataSource

    override fun create(): DataSource<Int, Photo> {
        photoDataSource= PhotoDataSource()
        dataSourceLiveData.postValue(photoDataSource)
        return photoDataSource
    }

}