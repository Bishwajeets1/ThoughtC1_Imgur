package com.example.imgurapiapp.source

import android.content.Context
import com.example.imgurapiapp.api.ImgurApiService
import com.example.imgurapiapp.model.ImgurImageResponse
import com.supply.blusip.com.data.RemoteDataSource
import com.supply.blusip.com.data.Result
import dagger.hilt.android.qualifiers.ApplicationContext

class DefaultImgurDataSource(
    @ApplicationContext private val applicationContext: Context,
    private val api:ImgurApiService
):ImgurDataSource,RemoteDataSource {
    override suspend fun getImages(
        section: String,
        sort: String,
        window: String,
        page: Int
    ): Result<ImgurImageResponse> {
        return getResult(applicationContext=applicationContext,
        api = {api.getImages(section, sort, window,page)})
    }

    override suspend fun searchImage(
        sort: String,
        window: String,
        page: Int,
        queary: String
    ): Result<ImgurImageResponse> {
        return  getResult(applicationContext = applicationContext,
        api = {api.searchImage(sort,window,page,queary)})
    }
}