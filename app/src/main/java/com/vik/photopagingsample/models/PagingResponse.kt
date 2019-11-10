package com.vik.photopagingsample.models

open class PagingResponse<T> {
    var isAllLoaded: Boolean? = null
    var isLoading: Boolean? = null
    var pageNumber: Int? = null
    var pageSize: Int? = null
    var data: T? = null
}