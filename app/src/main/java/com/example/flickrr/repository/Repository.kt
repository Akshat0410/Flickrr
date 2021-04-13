package com.example.flickrr.repository

import com.example.flickrr.Api.RetrofitInstance
import com.example.flickrr.Model.MainModel
import com.example.flickrr.Model.Photos

class Repository {

        suspend fun getPost(): MainModel {
        return RetrofitInstance.api.getPost()
    }
}