package com.example.imgurapiapp.api

import com.example.imgurapiapp.model.ImgurImageResponse
import org.jetbrains.annotations.TestOnly
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImgurApiService {
    @GET("gallery/{section}/{sort}/{window}/{page}")
    suspend fun getImages(
        @Path("section") section:String,
        @Path("sort") sort :String,
        @Path("window") window:String,
        @Path("page") page:Int
    ) : ImgurImageResponse

    @GET("gallery/search/{sort}/{window}/{page}")
    suspend fun searchImage(
        @Path("sort") sort :String,
        @Path("window") window:String,
        @Path("page") page:Int,
        @Query("q") query: String
    ):ImgurImageResponse


    @TestOnly
    @GET("gallery/{section}/{sort}/{window}/{page}")
    fun getImagesForTest(
        @Path("section") section:String,
        @Path("sort") sort :String,
        @Path("window") window:String,
        @Path("page") page:Int
    ) : Call<ImgurImageResponse>

    @TestOnly
    @GET("gallery/search/{sort}/{window}/{page}")
    fun searchImageForTest(
        @Path("sort") sort :String,
        @Path("window") window:String,
        @Path("page") page:Int,
        @Query("q") query: String
    ):Call<ImgurImageResponse>



}