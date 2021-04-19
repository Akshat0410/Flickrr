package com.example.flickrr.Model

data class Photos (

        //This model lies in between the MainModel and Photo Model

        val page: Int,
        val pages: Int,
        val perpage: Int,
        val total: Int,
        val photo: List<Photo>
)

