package com.example.flickrr


import android.app.DownloadManager
import android.content.BroadcastReceiver

import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.flickrr.Model.Photo
import com.bumptech.glide.request.target.Target
import android.net.Uri as Uri1

class RecyclerViewAdapter(private val context: Context,  data: List<Photo>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    private  var datalist =data
    var mydownloadId:Long=0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
         var image:ImageView = itemView.findViewById(R.id.Image)
        var title: TextView = itemView.findViewById(R.id.Title)
        var share: Button = itemView.findViewById(R.id.share)
        var download:Button = itemView.findViewById(R.id.download)
        var progress:ProgressBar = itemView.findViewById(R.id.prog)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.gallery, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//
        val data=datalist[position]
        holder.title.text=data.title
        Glide.with(context).load(data.url_s).listener( object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                holder.progress.visibility=View.VISIBLE
                return false
            }
            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                holder.progress.visibility=View.GONE
                return false

            }
        }).into(holder.image)



        holder.image.setOnClickListener {
            Toast.makeText(context, "Item Number $position Clicked - ", Toast.LENGTH_SHORT).show()
            gotoUrl(data.url_s)
        }

        holder.share.setOnClickListener {
            val shareIntent = Intent(ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(EXTRA_TEXT, "Goto this link - ${data.url_s}")
            val chooser=createChooser(shareIntent, "Share link Via .....")
            context.startActivity(chooser)
        }

        holder.download.setOnClickListener {
            val request=DownloadManager.Request(Uri1.parse(data.url_s)).
            setTitle(data.title).
            setDescription("Image Downloading").
            setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS,data.title).
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED).
            setAllowedOverMetered(true)

           val dm= context.getSystemService(Context.DOWNLOAD_SERVICE)  as DownloadManager
            mydownloadId=dm.enqueue(request)
        }

        val br=object : BroadcastReceiver(){
            override fun onReceive(p0: Context?, p1: Intent?) {
//                val id= p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1)
////                if(id==MydownloadId){}
            }

        }
        context.registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))



    }

    private fun gotoUrl(urlS: String) {
        val uris = Uri1.parse(urlS)
        val intents = Intent(ACTION_VIEW, uris)
        context.startActivity(intents)
    }






    override fun getItemCount(): Int {
        return datalist.size
    }

}

