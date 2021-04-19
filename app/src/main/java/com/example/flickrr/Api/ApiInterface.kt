package com.example.flickrr.Api

import com.example.flickrr.Model.MainModel
import com.example.flickrr.Model.Photo
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {


   @GET("services/rest/?method=flickr.photos.getRecent&per_page=100&page=1&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s")
   suspend fun getDatafromApi(@Query("page")  query : Int) : MainModel


   @GET("services/rest/?method=flickr.photos.search&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s")
   suspend  fun getdatawithsearch(@Query("text") text: String,@Query("page") query: Int): MainModel



}