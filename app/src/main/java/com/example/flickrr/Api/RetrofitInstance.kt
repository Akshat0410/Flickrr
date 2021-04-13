package com.example.flickrr.Api

import com.example.flickrr.utils.constants.Companion.url
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance  //Instance made obect to make it Singleton
{

 private val retrofit by lazy{
     Retrofit.Builder().
             baseUrl(url).
             addConverterFactory(GsonConverterFactory.create()).
             build()

 }

    val api : ApiInterface by lazy{
        retrofit.create(ApiInterface::class.java)
    }
}