package com.vik.photopagingsample.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vik.photopagingsample.R
import com.vik.photopagingsample.models.Photo
import com.vik.photopagingsample.network.Loaded
import com.vik.photopagingsample.network.NetworkStatus
import com.vik.photopagingsample.toColor



class PhotoPagedListAdapter :PagedListAdapter<Photo,RecyclerView.ViewHolder>(DIFF_CALLBACK){

    private var networkStatus: NetworkStatus? = null


    override fun getItemViewType(position: Int): Int =
        if (hasExtraRow() && position == itemCount - 1) 0 else 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==1){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
            return ItemViewHolder(view)
        }else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.bottom_loader, parent, false)
            return LoaderViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder){
            val item = getItem(position)
            holder.description.setBackgroundColor(item?.color.toColor())
            holder.description.text = item?.description ?: "-"
            Picasso.get().load(item?.urls?.regular).into(holder.image)
        }
    }

    companion object{
        val DIFF_CALLBACK=object :DiffUtil.ItemCallback<Photo>(){
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =oldItem.id==newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem == newItem

        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.image)
        val description=itemView.findViewById<TextView>(R.id.description)
    }

    class LoaderViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    private fun hasExtraRow() = networkStatus!=null && networkStatus != Loaded

    fun setNetworkStatus(newNetworkStatus:NetworkStatus){
        val previousState = networkStatus
        val previousExtraRow = hasExtraRow()
        networkStatus = newNetworkStatus
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && previousState !== newNetworkStatus) {
            notifyItemChanged(itemCount - 1)
        }
    }

}