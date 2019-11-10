package com.vik.photopagingsample.navigator

import com.vik.photopagingsample.network.RequestType

interface BaseNavigator<T> {

    fun inProgress(requestType: RequestType)
    fun onSuccess(data:T?)
    fun onFailed(message:String?,throwable: Throwable?)

}