package com.bendev.networkmanager.data.network.result

import java.net.ConnectException
import java.net.UnknownHostException

enum class NetworkError(
    val code: Int
) {
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    NO_CONNECTION(-1),
    UNKNOWN_ERROR(-2)
    ;

    companion object {
        fun fromCode(code: Int): NetworkError = values().find { it.code == code } ?: UNKNOWN_ERROR

        fun fromException(e: Exception): NetworkError = when (e) {
            is UnknownHostException,
            is ConnectException -> NO_CONNECTION
            else -> UNKNOWN_ERROR
        }
    }

    override fun toString(): String {
        return "ErrorType($name=(code: $code))"
    }
}