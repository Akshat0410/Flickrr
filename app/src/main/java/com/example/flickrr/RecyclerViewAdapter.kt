package com.example.flickrr

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flickrr.Model.Photo

class RecyclerViewAdapter(private val context: Context, data: List<Photo>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private  var datalist =data


    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
         var image:ImageView
         var title: TextView
        init{
            image=itemView.findViewById(R.id.Image)
            title=itemView.findViewById(R.id.Title)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.gallery,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var data=datalist[position]
        holder.title.text=data.title
        Glide.with(context).load(data.url_s).into(holder.image)

    }

    override fun getItemCount(): Int {
        return datalist.size
    }

}

