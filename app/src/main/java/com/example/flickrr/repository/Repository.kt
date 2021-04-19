package com.example.flickrr.repository

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.flickrr.Api.RetrofitInstance
import com.example.flickrr.ItemDataource
import com.example.flickrr.Model.MainModel
import com.example.flickrr.Model.Photo
import kotlinx.coroutines.flow.Flow

class Repository {

       fun getdatawithsearch(text: String): Flow<PagingData<Photo>> {
             return Pager(
            config = PagingConfig(pageSize = 6),
            pagingSourceFactory = { ItemDataource(RetrofitInstance.api,text) }
             ).flow

          }

    fun getDatafromApi(): Flow<PagingData<Photo>> {
        return Pager(
                config = PagingConfig(pageSize = 6),
                pagingSourceFactory = { ItemDataource(RetrofitInstance.api,"null") }
        ).flow
    }
}