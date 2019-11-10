package com.vik.photopagingsample.observer

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class BroadcastLiveData <T>:MutableLiveData<T>(){

    private var lastObserver: Observer<T>? = null

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            if (lastObserver!=observer){
                observer.onChanged(it)
            }
        })
    }

    fun setValue(value: T,observer: Observer<T>){
        lastObserver=observer
        setValue(value)
    }


}