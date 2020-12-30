package com.example.tab2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tab2.R
import com.example.tab2.model.MediaFileData

class GalleryAdapter(private val context: Context, private val dataset: List<MediaFileData>):RecyclerView.Adapter<GalleryAdapter.ImageViewHolder>() {
        class ImageViewHolder(private val view: View): RecyclerView.ViewHolder(view){
            val imageView:ImageView = view.findViewById(R.id.item_image)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            return ImageViewHolder(adapterLayout)
        }

        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = dataset.getOrNull(position)

        Glide.with(holder.imageView)
            .load(item!!.uri)
            .thumbnail(0.33f)
            .centerCrop()
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}