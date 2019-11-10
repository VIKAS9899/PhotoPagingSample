package com.vik.photopagingsample.network

sealed class Resource<T>{
    data class Success<T>(val data:T?):Resource<T>()
    data class Failure<T>(val message:String?,val throwable: Throwable?):Resource<T>()
    data class InProgress<T>(val data:T?):Resource<T>()
}
