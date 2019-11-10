package com.vik.photopagingsample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.activity_main.list
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.fragment_tab.*
import kotlinx.android.synthetic.main.layout_error.*

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

        initViews()

    }

    private fun initViews() {
        initRV()

        val isFreshRequest = mMainViewModel.isRequesting && !mMainViewModel.isLoadingMore
        val shouldShowErrorBanner = !mMainViewModel.isRequesting && mMainViewModel.photos.isEmpty()

        progressBar?.visibility=if (isFreshRequest) View.VISIBLE else View.GONE
        errorLayout?.visibility=if (shouldShowErrorBanner) View.VISIBLE else View.GONE
        errorTV?.text=mMainViewModel.errorMessage

        //swipe to refresh Layout Listener
        swipeToRefresh?.setOnRefreshListener {
            if (mMainViewModel.isRequesting){
                swipeToRefresh?.isRefreshing=false
            }else{
                mMainViewModel.loadPhotos(RequestType.REFRESH)
            }
        }

        retryBtn.setOnClickListener {
            if (!mMainViewModel.isRequesting){
                mMainViewModel.loadPhotos(RequestType.FRESH)
            }
        }
    }

    private fun initRV() {
        adapter=PhotoPagedListAdapter(mMainViewModel)
        list?.adapter=adapter
        list?.addOnScrollListener(listener)
        list?.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }


    private val listener= object : RVOnScrollListener() {

        override fun onLoadMore() {
            mMainViewModel.loadPhotos(RequestType.LOAD_MORE)
        }

        override fun isRequesting(): Boolean = mMainViewModel.isRequesting

        override fun hasMore(): Boolean = mMainViewModel.hasMore

    }

    override fun inProgress(requestType: RequestType) {
        errorLayout?.visibility = View.GONE
        when(requestType){
            RequestType.LOAD_MORE->{
                adapter.addFooter()
            }
            RequestType.REFRESH->{

            }
            RequestType.FRESH->{
                progressBar?.visibility = View.VISIBLE
            }
        }
    }

    override fun onSuccess(data: ArrayList<Photo>?) {
        swipeToRefresh?.isRefreshing=false
        errorLayout?.visibility=View.GONE
        progressBar?.visibility=View.GONE
        if (mMainViewModel.isLoadingMore){
            adapter.removeFooter()
            val oldListSize=mMainViewModel.photos.size
            mMainViewModel.photos.addAll(data?: emptyList())
            adapter.notifyItemRangeInserted(oldListSize,data?.size?:0)
        }else{
            mMainViewModel.photos.clear()
            mMainViewModel.photos.addAll(data?: emptyList())
            adapter.notifyDataSetChanged()
        }
    }

    override fun onFailed(message: String?, throwable: Throwable?) {

        swipeToRefresh?.isRefreshing=false
        errorLayout?.visibility=if (mMainViewModel.photos.isEmpty()) View.VISIBLE else View.GONE
        errorTV?.text=mMainViewModel.errorMessage

        adapter.removeFooter()
        progressBar?.visibility=View.GONE
    }
}