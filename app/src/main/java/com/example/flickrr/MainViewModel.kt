package com.example.flickrr

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrr.Model.MainModel
import com.example.flickrr.Model.Photos
import com.example.flickrr.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

   val MyResponse :MutableLiveData<MainModel> = MutableLiveData()
   val MyResponse2 :MutableLiveData<MainModel> = MutableLiveData()


    fun getPost() {
        viewModelScope.launch {
            val response=repository.getPost()
            MyResponse.value = response
        }

    }

    fun getPost2(value : String) {
        viewModelScope.launch {
            val response=repository.getPost2(value)
            MyResponse2.value = response
        }

    }
}