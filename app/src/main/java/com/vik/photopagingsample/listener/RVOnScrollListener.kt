package com.vik.photopagingsample.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class RVOnScrollListener: RecyclerView.OnScrollListener(){

    abstract fun onLoadMore()
    abstract fun isRequesting():Boolean
    abstract fun hasMore():Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager
        if (layoutManager !is LinearLayoutManager) {
            return
        }
        val lastVisible = layoutManager.findLastVisibleItemPosition()
        val item = layoutManager.itemCount
        if (!isRequesting() && hasMore() && item > 0 && lastVisible + 2 >= item) {
            recyclerView.post {
                onLoadMore()
            }
        }

    }
}