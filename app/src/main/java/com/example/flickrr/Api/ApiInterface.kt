package com.example.flickrr.Api

import com.example.flickrr.Model.MainModel
import retrofit2.http.GET


interface ApiInterface {

   @GET("?method=flickr.photos.getRecent&per_page=100&page=1&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s")
   suspend fun getPost(): MainModel
}