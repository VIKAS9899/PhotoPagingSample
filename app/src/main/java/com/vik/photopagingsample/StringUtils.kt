package com.vik.photopagingsample

import android.graphics.Color

fun String?.toColor():Int{
    return try {
        Color.parseColor(this)
    }catch (e:Exception){
        Color.BLACK
    }
}