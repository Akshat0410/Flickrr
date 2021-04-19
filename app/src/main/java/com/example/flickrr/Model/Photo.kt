package com.example.flickrr.Model

data class Photo (

         //This depicts the lowest model in the API hierarchy

        val id: String,
        val owner: String,
        val secret: String,
        val server: String,
        val farm: Int,
        val title: String,
        val ispublic: String,
        val isfriend: Int,
        val isfamily: Int,
        val url_s : String,
        val height_s: Int,
        val width_s: Int

        )
