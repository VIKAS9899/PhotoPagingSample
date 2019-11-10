package com.vik.photopagingsample.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vik.photopagingsample.constants.Constant
import java.lang.ref.WeakReference

open class BaseViewModel2<T>(application: Application) :AndroidViewModel(application){

    var isRequesting: Boolean = false
    var isLoadingMore: Boolean = false
    var errorMessage: String? = null
    var pageNumber: Int = 1

    open var pageSize: Int = Constant.DefaultPageSize



    private var weakNavigator: WeakReference<T?>? = null


    fun setNavigator(navigator:T?){
        weakNavigator = WeakReference(navigator)
    }

    fun getNavigator():T?{
        return weakNavigator?.get()
    }

    fun incrementPage(){
        pageNumber++
    }
    fun resetPageNumber(){
        pageNumber=1
    }

}