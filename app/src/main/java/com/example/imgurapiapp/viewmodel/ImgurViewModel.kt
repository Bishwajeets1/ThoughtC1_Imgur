package com.example.imgurapiapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.imgurapiapp.model.ImgurImageResponse
import com.example.imgurapiapp.repository.ImgurRepository
import com.supply.blusip.com.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImgurViewModel @Inject constructor(
    private val imgurRepository: ImgurRepository
) : ViewModel() {

    fun getImages(
        section: String, sort: String, window: String,page:Int
    ): LiveData<Result<ImgurImageResponse>> = liveData {
        emit(Result.Loading)
        emit(imgurRepository.getImages(section, sort, window,page))
    }

    fun searchImages(
        sort :String, window:String,page:Int ,queary:String
    ):LiveData<Result<ImgurImageResponse>> = liveData {
        emit(Result.Loading)
        emit(imgurRepository.searchImage(sort, window, page,queary))
    }
}