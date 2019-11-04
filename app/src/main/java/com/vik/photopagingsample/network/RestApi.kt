package com.vik.photopagingsample.network

import com.vik.photopagingsample.models.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("/photos")
    fun fetchPhotos(
        @Query("pageSize") pageSize:Int,
        @Query("pageNumber") pageNumber: Int
    ):Call<ArrayList<Photo>>

}

