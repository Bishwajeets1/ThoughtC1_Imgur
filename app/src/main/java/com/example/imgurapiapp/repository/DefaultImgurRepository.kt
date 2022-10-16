package com.example.imgurapiapp.repository

import android.content.Context
import com.example.imgurapiapp.model.ImgurImageResponse
import com.example.imgurapiapp.source.ImgurDataSource
import com.supply.blusip.com.data.Result
import dagger.hilt.android.qualifiers.ApplicationContext

class DefaultImgurRepository(
    @ApplicationContext private val applicationContext: Context,
    private val remote: ImgurDataSource
) : ImgurRepository {
    override suspend fun getImages(
        section: String,
        sort: String,
        window: String,
        page: Int
    ): Result<ImgurImageResponse> {
        return remote.getImages(section, sort, window,page)
    }

    override suspend fun searchImage(
        sort: String,
        window: String,
        page: Int,
        queary: String
    ): Result<ImgurImageResponse> {
        return remote.searchImage( sort, window, page, queary)
    }
}