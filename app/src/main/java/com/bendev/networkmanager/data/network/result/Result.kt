package com.bendev.networkmanager.data.network.result

sealed class Result<out T: Any> {

    data class Success<out T : Any>(val data: T?) : Result<T>()
    data class Error(val error: NetworkError) : Result<Nothing>()

    override fun toString(): String = when (this) {
        is Success<*> -> "Success[data=$data]"
        is Error -> "Error[error=$error]"
    }

}