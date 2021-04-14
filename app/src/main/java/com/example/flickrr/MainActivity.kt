package com.example.flickrr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrr.Model.Photo
import com.example.flickrr.repository.Repository


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter:RecyclerViewAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var dataList:List<Photo>
        recyclerView=findViewById(R.id.galrecycler)
        val repository=Repository()
        val viewModelFactory=MainViewModelFactory(repository)
        viewModel=ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPost()
        viewModel.MyResponse.observe(this, { response ->
            dataList= response.photos.photo
            recyclerView.layoutManager=GridLayoutManager(applicationContext,2)
            adapter= RecyclerViewAdapter(this, dataList)
            recyclerView.adapter=adapter
        })



    }
}


