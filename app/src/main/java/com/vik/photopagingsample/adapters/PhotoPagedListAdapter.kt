package com.vik.photopagingsample.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vik.photopagingsample.R
import com.vik.photopagingsample.models.Photo
import com.vik.photopagingsample.toColor
import com.vik.photopagingsample.viewModels.MainViewModel2


class PhotoPagedListAdapter(private val mViewModel:MainViewModel2)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val photos: ArrayList<Photo> = mViewModel.photos


    override fun getItemCount(): Int=photos.size+if (mViewModel.isLoadingMore) 1 else 0




    override fun getItemViewType(position: Int): Int =
        if (mViewModel.isLoadingMore && position==itemCount-1) 0 else 1


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
            val item = photos?.get(position)
            holder.description.setBackgroundColor(item?.color.toColor())
            holder.description.text = item?.description ?: "-"
            Picasso.get().load(item?.urls?.regular).into(holder.image)
        }
    }

    fun addFooter(){
        if (!mViewModel.isLoadingMore) {
            mViewModel.isLoadingMore=true
            notifyItemInserted(itemCount-1)
        }
    }
    fun removeFooter(){
        if (mViewModel.isLoadingMore) {
            mViewModel.isLoadingMore=false
            notifyItemRemoved(itemCount)
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.image)
        val description=itemView.findViewById<TextView>(R.id.description)
    }

    class LoaderViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

}