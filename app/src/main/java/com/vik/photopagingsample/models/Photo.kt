package com.vik.photopagingsample.models

data class Photo(
    val id:String,
    val urls:Url?,
    val description:String?,
    val color:String?
)
data class Url(
    val raw:String?,
    val full:String?,
    val regular:String?,
    val small:String?,
    val thumb:String?
)