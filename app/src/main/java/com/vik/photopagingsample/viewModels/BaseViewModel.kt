package com.vik.photopagingsample.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vik.photopagingsample.constants.Constant
import java.util.concurrent.atomic.AtomicBoolean

open class BaseViewModel(application: Application) :AndroidViewModel(application){

    var pageNumber: Int = 1
    val isRequesting = AtomicBoolean(false)

    open fun getPageSize(): Int = Constant.DefaultPageSize

    fun incrementPageNumber(){
        pageNumber++
    }

    fun resetPageNumber(){
        pageNumber = 1
    }

}