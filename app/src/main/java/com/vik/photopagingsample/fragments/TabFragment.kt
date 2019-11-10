package com.vik.photopagingsample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.vik.photopagingsample.R
import com.vik.photopagingsample.adapters.PhotoPagedListAdapter
import com.vik.photopagingsample.listener.RVOnScrollListener
import com.vik.photopagingsample.models.Photo
import com.vik.photopagingsample.navigator.MainNavigator
import com.vik.photopagingsample.network.RequestType
import com.vik.photopagingsample.viewModels.MainViewModel2
import kotlinx.android.synthetic.main.activity_main.*

class TabFragment :Fragment(), MainNavigator {


    lateinit var mMainViewModel: MainViewModel2
    lateinit var adapter: PhotoPagedListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val key = arguments?.getInt("pageNumber") ?: 0
        mMainViewModel=ViewModelProviders.of(this).get(key.toString(), MainViewModel2::class.java)
        mMainViewModel.setNavigator(this)
        adapter=PhotoPagedListAdapter(mMainViewModel)
        list.adapter=adapter
        list.addOnScrollListener(listener)
        list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        val isFreshRequest = mMainViewModel.isRequesting && !mMainViewModel.isLoadingMore

        progressBar.visibility=if (isFreshRequest) View.VISIBLE else View.GONE
    }


    private val listener= object : RVOnScrollListener() {

        override fun onLoadMore() {
            mMainViewModel.loadPhotos(RequestType.LOAD_MORE)
        }

        override fun isRequesting(): Boolean = mMainViewModel.isRequesting

        override fun hasMore(): Boolean = mMainViewModel.hasMore

    }

    override fun inProgress(requestType: RequestType) {
        if (requestType==RequestType.LOAD_MORE){
            adapter.addFooter()
        }else{
            progressBar?.visibility=View.VISIBLE
        }
    }

    override fun onSuccess(data: ArrayList<Photo>?) {
        if (mMainViewModel.isLoadingMore){
            adapter.removeFooter()
            val oldListSize=mMainViewModel.photos.size
            mMainViewModel.photos.addAll(data?: emptyList())
            adapter.notifyItemRangeInserted(oldListSize,data?.size?:0)
        }else{
            progressBar?.visibility=View.GONE
            mMainViewModel.photos.clear()
            mMainViewModel.photos.addAll(data?: emptyList())
            adapter.notifyDataSetChanged()
        }
    }

    override fun onFailed(message: String?, throwable: Throwable?) {
        adapter.removeFooter()
        progressBar.visibility=View.GONE
        Toast.makeText(context,
            message?:throwable?.message?: "Error message!",
            Toast.LENGTH_SHORT).show()
    }
}