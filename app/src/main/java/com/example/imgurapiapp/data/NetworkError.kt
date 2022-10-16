package com.supply.blusip.com.data

/**
 * Created by Prasada Rao on 30/10/20 10:46 PM
 **/
data class NetworkError(
    val errorCode: Int,
    val errorMessage: String,
    val errorKind: NetworkErrorKind = NetworkErrorKind.HTTP
)