package com.vik.photopagingsample.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.vik.photopagingsample.models.Photo
import com.vik.photopagingsample.network.RestApiFactory
import com.vik.photopagingsample.network.NetworkStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PhotoDataSource :PageKeyedDataSource<Int,Photo>(){

    var stateLiveData: MutableLiveData<NetworkStatus> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Photo>
    ) {

        stateLiveData.postValue(NetworkStatus.onInitialLoad())
        RestApiFactory.sInstance.fetchPhotos(params.requestedLoadSize,1)
            .enqueue(object : Callback<ArrayList<Photo>> {
                override fun onResponse(call: Call<ArrayList<Photo>>, response: Response<ArrayList<Photo>>) {
                    if (response.isSuccessful) {
                        callback.onResult(response.body()!!, null, 2)
                        stateLiveData.postValue(NetworkStatus.onLoaded())

                    } else {
                        stateLiveData.postValue(NetworkStatus.onFailed(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<ArrayList<Photo>>, t: Throwable) {
                    stateLiveData.postValue(NetworkStatus.onFailed(t.message))
                }
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        stateLiveData.postValue(NetworkStatus.onLoadMore())
        RestApiFactory.sInstance.fetchPhotos(params.requestedLoadSize,params.key)
            .enqueue(object : Callback<ArrayList<Photo>> {
                override fun onResponse(call: Call<ArrayList<Photo>>, response: Response<ArrayList<Photo>>) {
                    if (response.isSuccessful) {
                        callback.onResult(response.body()!!,params.key+1)
                        stateLiveData.postValue(NetworkStatus.onLoaded())

                    } else {
                        stateLiveData.postValue(NetworkStatus.onFailed(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<ArrayList<Photo>>, t: Throwable) {
                    stateLiveData.postValue(NetworkStatus.onFailed(t.message))
                }
            })

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}