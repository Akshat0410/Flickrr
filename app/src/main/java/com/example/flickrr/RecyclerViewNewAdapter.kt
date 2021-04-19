package com.example.flickrr

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.flickrr.Model.Photo
import com.example.flickrr.RecyclerViewNewAdapter.*

class RecyclerViewNewAdapter(context: Context) :PagingDataAdapter<Photo, MyViewHolder>(DiffUtilCallBack()){


    private var context=context

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(context, it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater=LayoutInflater.from(parent.context).inflate(R.layout.gallery,parent,false)
        return MyViewHolder(inflater)
    }

    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view){


        var mydownloadId:Long=0
        var image:ImageView = itemView.findViewById(R.id.Image)
        var title: TextView = itemView.findViewById(R.id.Title)
        var share: Button = itemView.findViewById(R.id.share)
        var download:Button = itemView.findViewById(R.id.download)
        var progress:ProgressBar = itemView.findViewById(R.id.prog)
        fun bind(context: Context,data : Photo){
             title.text=data.title
            Glide.with(image).load(data.url_s).placeholder(R.drawable.placeholder).listener(object :
                RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progress.visibility = View.VISIBLE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {

                    progress.visibility = View.GONE
                    return false

                }
            }).error(R.drawable.ic_baseline_error_24).into(image)


            image.setOnClickListener {
                Toast.makeText(context, "Item Number $position Clicked - ", Toast.LENGTH_SHORT).show()
//                gotoUrl(data.url_s)
            }

            share.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Goto this link - ${data.url_s}")
                val chooser= Intent.createChooser(shareIntent, "Share link Via .....")
                context.startActivity(chooser)
            }

            download.setOnClickListener {
                val request=DownloadManager.Request(Uri.parse(data.url_s)).
                setTitle(data.title).
                setDescription("Image Downloading").
                setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, data.title).
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

//        private fun gotoUrl(urlS: String) {
//            val uris = Uri1.parse(urlS)
//            val intents = Intent(ACTION_VIEW, uris)
//            context.startActivity(intents)
//        }
    }

    class DiffUtilCallBack: DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.title==newItem.title && oldItem.url_s==newItem.url_s
        }

    }

}





