package com.example.imgurapiapp.model


import com.google.gson.annotations.SerializedName

data class ImgurImageResponse(
    @SerializedName("data")
    val data: ArrayList<ImgurData>,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)