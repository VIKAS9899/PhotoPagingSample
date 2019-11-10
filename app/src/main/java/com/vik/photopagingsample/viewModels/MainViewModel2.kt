package com.vik.photopagingsample.viewModels

import android.app.Application
import com.vik.photopagingsample.constants.Constant
import com.vik.photopagingsample.models.Photo
import com.vik.photopagingsample.navigator.MainNavigator
import com.vik.photopagingsample.network.RequestType
import com.vik.photopagingsample.network.RestApiFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel2(application: Application):BaseViewModel2<MainNavigator>(application){

    val photos = ArrayList<Photo>()
    var hasMore: Boolean = true


    init {
        loadPhotos()
    }

     fun loadPhotos(requestType: RequestType=RequestType.FRESH) {

        if (requestType==RequestType.FRESH || requestType==RequestType.REFRESH){
            resetPageNumber()
        }
         isRequesting=true
         getNavigator()?.inProgress(requestType)
        RestApiFactory.sInstance.fetchPhotos(pageSize, pageNumber)
            .enqueue(object : Callback<ArrayList<Photo>> {
                override fun onResponse(
                    call: Call<ArrayList<Photo>>,
                    response: Response<ArrayList<Photo>>
                ) {
                    val listSize = response.body()?.size ?: 0
                    hasMore = !(listSize==0 || listSize%pageSize>0)

                    if (response.isSuccessful) {
                        incrementPage()
                        getNavigator()?.onSuccess(response.body())
                    } else {
                        getNavigator()?.onFailed(response.errorBody()?.string(),null)
                    }
                    isRequesting=false
                }

                override fun onFailure(call: Call<ArrayList<Photo>>, t: Throwable) {
                    isRequesting=false
                    getNavigator()?.onFailed(t.message,t)
                }
            })

    }



}