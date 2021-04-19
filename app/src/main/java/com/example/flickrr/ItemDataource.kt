package com.example.flickrr

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.flickrr.Api.ApiInterface
import com.example.flickrr.Model.Photo
import retrofit2.http.POST
import java.lang.Exception

class ItemDataource(val apiservice: ApiInterface,val input:String)  : PagingSource<Int, Photo>(){
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {

        return try {
            val position = params.key ?: FIRST_PAGE
            val response =if(input.length==0)
                          apiservice.getDatafromApi(position)
                        else
                          apiservice.getdatawithsearch(input,position)
            val photos = response.photos.photo

            LoadResult.Page(data = photos,
            prevKey = if (position == FIRST_PAGE) null else position - 1,
            nextKey = if (photos.isEmpty()) null else position+1
            )
            
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }

    }

    companion object{
        private const val FIRST_PAGE=1
    }



}