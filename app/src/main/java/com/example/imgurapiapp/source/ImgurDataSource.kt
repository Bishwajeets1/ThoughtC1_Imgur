package com.example.imgurapiapp.source

import com.example.imgurapiapp.model.ImgurImageResponse
import com.supply.blusip.com.data.Result

interface ImgurDataSource {

    suspend fun getImages(
        section:String,sort :String, window:String,page:Int
    ) : Result<ImgurImageResponse>

    suspend fun searchImage(
       sort :String, window:String,page:Int, queary:String
    ):Result<ImgurImageResponse>
}