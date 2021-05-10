package com.primer

import com.google.gson.Gson
import org.joda.time.Instant

import java.util.Base64

object TokenUtils {

    fun isStillValid(expirationDate: String): Boolean {
        return Instant.parse(expirationDate).isAfterNow
    }

    fun convertToToken(token: String): TokenData {
        val splitString = token.split(".")
        val userCredentials = String(
            Base64.getDecoder().decode(if (splitString.size > 1) splitString[1] else splitString[0])
        )
        return Gson().fromJson(userCredentials, TokenData::class.java)
    }
}

data class TokenData(
    val accessToken: String,
    val analyticsUrl: String? = null,
    val intent: String? = null,
    val configurationUrl: String? = null,
    val coreUrl: String? = null,
    val pciUrl: String? = null,
    val env: String? = null,
    val threeDSecureInitUrl: String? = null,
    val threeDSecureToken: String? = null,
    val paymentFlow: String? = null
)
