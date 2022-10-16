package com.supply.blusip.com.data

import android.content.Context
import com.example.imgurapiapp.R
import com.example.imgurapiapp.utils.HelperUtility



interface RemoteDataSource: FailureHandler {

    /**
     * Method to prepare the [Result] by executing the API request.
     */
    suspend fun <T> getResult(applicationContext: Context, api: suspend () -> T): Result<T> {
        return if (HelperUtility.InternetCheck(applicationContext)) {
            try {
                Result.Success(api())
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Failure(parseNetworkError(applicationContext, e))
            }
        } else {
            Result.Failure(
                NetworkError(
                    100,
                    HelperUtility.getStringByLocale(
                        applicationContext, R.string.network_error_poor_internet
                    ), NetworkErrorKind.INTERNET
                )
            )
        }
    }

    /**
     * Method to prepare the [Result] by executing the API request.
     */
    suspend fun <NetworkModel, DomainModel> getResult(
        applicationContext: Context,
        api: suspend () -> NetworkModel,
        dataConverter: (NetworkModel) -> DomainModel
    ): Result<DomainModel> {
        return if (HelperUtility.InternetCheck(applicationContext)) {
            try {
                Result.Success(dataConverter(api()))
            } catch (e: Exception) {
                Result.Failure(parseNetworkError(applicationContext, e))
            }
        } else {
          //  CustomView.showToastMessage(applicationContext, applicationContext.getString(R.string.network_error_poor_internet))
            Result.Failure(
                NetworkError(
                    100,
                    HelperUtility.getStringByLocale(
                        applicationContext, R.string.network_error_poor_internet
                    ), NetworkErrorKind.INTERNET
                )
            )
        }
    }
}