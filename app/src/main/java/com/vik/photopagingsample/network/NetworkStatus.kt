package com.vik.photopagingsample.network

sealed class NetworkStatus{
    companion object{
        fun onInitialLoad(): NetworkStatus = InitialLoading
        fun onLoadMore(): NetworkStatus = Loading
        fun onLoaded(): NetworkStatus = Loaded
        fun onFailed(message: String?): NetworkStatus = Failed(message)
    }
}
object InitialLoading : NetworkStatus()
object Loading : NetworkStatus()
object Loaded : NetworkStatus()
data class Failed(val message: String?) : NetworkStatus()