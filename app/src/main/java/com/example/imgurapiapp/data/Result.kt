package com.supply.blusip.com.data

sealed class Result<out T> {

    data class Success<R>(val data: R?): Result<R>()

    data class Failure(val networkError: NetworkError): Result<Nothing>()

    object Loading: Result<Nothing>()
}