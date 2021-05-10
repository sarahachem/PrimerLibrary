package com.primer

import retrofit2.Response

fun <T> Response<T>.checkSuccessful(): Response<T> {
    if (!isSuccessful) {
        when {
            code() == 422 -> throw IllegalStateException("Wrong card details")
            code() == 403 -> throw IllegalAccessException("Access token not valid")
            else -> throw Exception("Tokenization error: ${message()} (code: ${code()})")
        }
    }
    return this
}