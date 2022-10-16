package com.supply.blusip.com.data

import android.os.Parcelable
import android.text.TextUtils
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DefaultResponseModel(
        @SerializedName("message")
         var message: String? = null,
        @SerializedName("status")
         var status: Int? = null,
) : Parcelable