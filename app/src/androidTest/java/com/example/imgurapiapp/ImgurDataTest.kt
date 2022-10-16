package com.example.imgurapiapp

import com.example.imgurapiapp.api.ImgurApiService
import com.example.imgurapiapp.model.ImgurImageResponse
import com.supply.blusip.com.data.di.RetrofitModule
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class ImgurDataTest {

    @Test
    fun getImageApiResponse() {
        val imgurApiService: ImgurApiService = RetrofitModule.provideDefaultRetrofit().create(ImgurApiService::class.java)
        val call: Call<ImgurImageResponse> = imgurApiService.getImagesForTest("top","time","week",1)

        try{
            val response: Response<ImgurImageResponse> = call.execute()
            val imgurResponse: ImgurImageResponse? = response.body()
            assert(imgurResponse?.success?:false)
        }catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun getSearchImageApiResponse() {
        val imgurApiService: ImgurApiService = RetrofitModule.provideDefaultRetrofit().create(ImgurApiService::class.java)
        val call: Call<ImgurImageResponse> = imgurApiService.searchImageForTest("top","time" ,1,"cat")

        try{
            val response: Response<ImgurImageResponse> = call.execute()
            val imgurResponse: ImgurImageResponse? = response.body()
            assert(imgurResponse?.success?:false)
        }catch (e: IOException) {
            e.printStackTrace()
        }
    }

}