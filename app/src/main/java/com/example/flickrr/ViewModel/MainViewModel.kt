package com.example.flickrr.ViewModel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.flickrr.Model.MainModel
import com.example.flickrr.Model.Photo
import com.example.flickrr.Model.Photos
import com.example.flickrr.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch



class MainViewModel(private val repository: Repository) : ViewModel() {

    val job = Job()
    val scope = CoroutineScope(job + Dispatchers.IO)
    fun getdatawithsearch(value : String) :Flow<PagingData<Photo>>{
    val newSearchResult: Flow<PagingData<Photo>> = repository.getdatawithsearch(value)
            .cachedIn(scope)
    return newSearchResult

    }

    fun getDatafromApi(): Flow<PagingData<Photo>> {

        val newResult: Flow<PagingData<Photo>> = repository.getDatafromApi()
            .cachedIn(scope)
       return newResult
    }
}


