package com.example.proj1_tablayout.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proj1_tablayout.OpenGalleryFolderActivity
import com.example.proj1_tablayout.R
import com.example.proj1_tablayout.ShowFullImageActivity
import com.example.proj1_tablayout.model.MediaFileData

class InGalleryImageAdapter(private val context: Context, private val dataset: List<MediaFileData>):
        RecyclerView.Adapter<InGalleryImageAdapter.inGalleryImageHolder>() {
    class inGalleryImageHolder(private val view: View): RecyclerView.ViewHolder(view){
        val imageView: ImageView = view.findViewById(R.id.gridImg_infolder)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): inGalleryImageHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return inGalleryImageHolder(adapterLayout)
    }


    override fun onBindViewHolder(holder: inGalleryImageHolder, position: Int) {
        val item = dataset.getOrNull(position)

        //holder.gridSub.text = item!!.dateTaken.toString()

        holder.imageView.setOnClickListener{
            val intent: Intent = Intent(context, ShowFullImageActivity::class.java)
            intent.putExtra("uri", item!!.uri.toString())
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