package com.example.imgurapiapp.repository

import com.example.imgurapiapp.model.ImgurImageResponse
import com.supply.blusip.com.data.Result

interface ImgurRepository {

    suspend fun getImages(
         section:String,sort :String, window:String,page:Int
    ) : Result<ImgurImageResponse>

    suspend fun searchImage(
    sort :String, window:String,page:Int,queary:String
    ):Result<ImgurImageResponse>
}