package com.vik.photopagingsample.models

sealed class Resource<T>{

    data class Failed<T>(val message:String?,val throwable: Throwable?):Resource<T>()
    data class Success<T>(val data:T):Resource<T>()
    data class Loading<T>(val data:T):Resource<T>()
}