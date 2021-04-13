package com.example.flickrr

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrr.Model.Photo
import com.example.flickrr.Model.Photos
import com.example.flickrr.repository.Repository
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter:RecyclerViewAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var dataList:List<Photo>
        recyclerView=findViewById(R.id.galrecycler)
        val repository=Repository()
        val viewModelFactory=MainViewModelFactory(repository)
        viewModel=ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPost()
        viewModel.MyResponse.observe(this, Observer { response ->
            dataList= response.photos.photo
            recyclerView.layoutManager=GridLayoutManager(applicationContext,2)
            adapter= RecyclerViewAdapter(this, dataList)
            recyclerView.adapter=adapter
        })


    }
}