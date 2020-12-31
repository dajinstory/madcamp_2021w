package com.example.proj1_tablayout.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proj1_tablayout.OpenGalleryFolderActivity
import com.example.proj1_tablayout.R
import com.example.proj1_tablayout.model.MediaFileData

class GalleryAdapter(private val context: Context, private val dataset: List<MediaFileData>):
    RecyclerView.Adapter<GalleryAdapter.ImageViewHolder>() {
    class ImageViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val imageView: ImageView = view.findViewById(R.id.gridImg)
        val gridName: TextView = view.findViewById(R.id.gridName)
        //val gridSub: TextView = view.findViewById(R.id.gridSub)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.folder_image_item, parent, false)
        return ImageViewHolder(adapterLayout)
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = dataset.getOrNull(position)
        holder.gridName.text = item!!.bucketName

        //holder.gridSub.text = item!!.dateTaken.toString()

        holder.imageView.setOnClickListener {
            //Toast.makeText(context, "Clicked", LENGTH_SHORT).show()
            val intent:Intent = Intent(context, OpenGalleryFolderActivity::class.java)
            intent.putExtra("bucketId", item.bucketId)
            context.startActivity(intent)
        }

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