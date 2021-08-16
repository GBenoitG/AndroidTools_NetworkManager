package com.bendev.networkmanager.data.network.services

import com.bendev.networkmanager.data.network.result.NetworkError
import com.bendev.networkmanager.data.network.result.Result
import retrofit2.Response
// TODO Add Timber dependency
//import timber.log.Timber

abstract class BaseDataSource {

    protected suspend fun <T : Any> getResult(networkCall: suspend () -> Response<T>): Result<T> {
        try {
            val response = networkCall()
            if (response.isSuccessful) {
                val body = response.body()
                return Result.Success(body)
            }
            return error(NetworkError.fromCode(response.code()))
        } catch (e: Exception) {
            // TODO Add Timber dependency
            //Timber.e(e)
            return error(NetworkError.fromException(e))
        }
    }

    private fun error(errorType: NetworkError) : Result.Error {
        // TODO Add Timber dependency
        //Timber.w(errorType.toString())
        return Result.Error(errorType)
    }

}