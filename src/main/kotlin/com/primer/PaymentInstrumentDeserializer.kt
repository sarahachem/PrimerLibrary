package com.primer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class TokenizedPaymentDeserializer : JsonDeserializer<TokenizedPaymentInstrumentInfo> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): TokenizedPaymentInstrumentInfo {
        return deserializePaymentInstrumentInfo(json.asJsonObject)
    }

    private fun deserializePaymentInstrumentInfo(paymentJsonObject: JsonObject): TokenizedPaymentInstrumentInfo {
        return when (paymentJsonObject["paymentInstrumentType"]?.takeIf { it.isJsonNull.not() }?.asString) {
            "PAYMENT_CARD" -> getTokenizedCardInfo(paymentJsonObject)
            "PAYPAL_ORDER" -> getTokenizedPaypalInfo(paymentJsonObject)
            else -> throw (Exception("Payment type not supported"))
        }
    }

    private fun getTokenizedCardInfo(ownerJsonObject: JsonObject): TokenizedCardInfo {
        val paymentInstrumentDataJson = ownerJsonObject["paymentInstrumentData"].asJsonObject
        return TokenizedCardInfo(
            deleted = ownerJsonObject["deleted"]?.takeIf { it.isJsonNull.not() }?.asBoolean
                ?: false,
            createdAt = ownerJsonObject["createdAt"]?.takeIf { it.isJsonNull.not() }?.asString,
            updatedAt = ownerJsonObject["updatedAt"]?.takeIf { it.isJsonNull.not() }?.asString,
            token = ownerJsonObject["token"]?.takeIf { it.isJsonNull.not() }?.asString,
            analyticsId = ownerJsonObject["analyticsId"]?.takeIf { it.isJsonNull.not() }?.asString,
            tokenType = ownerJsonObject["tokenType"]?.takeIf { it.isJsonNull.not() }?.asString,
            paymentInstrumentType = ownerJsonObject["paymentInstrumentType"]?.takeIf { it.isJsonNull.not() }?.asString,
            paymentInstrumentData = paymentInstrumentDataJson.toCardData()
        )
    }

    private fun getTokenizedPaypalInfo(ownerJsonObject: JsonObject): TokenizedPaypalInfo {
        val paymentInstrumentDataJson =
            ownerJsonObject["paymentInstrumentData"]?.takeIf { it.isJsonNull.not() }?.asJsonObject
        val threeDSecureAuthenticationJson =
            ownerJsonObject["threeDSecureAuthentication"]?.takeIf { it.isJsonNull.not() }?.asJsonObject
        return TokenizedPaypalInfo(
            deleted = ownerJsonObject["deleted"]?.takeIf { it.isJsonNull.not() }?.asBoolean
                ?: false,
            createdAt = ownerJsonObject["createdAt"]?.takeIf { it.isJsonNull.not() }?.asString,
            updatedAt = ownerJsonObject["updatedAt"]?.takeIf { it.isJsonNull.not() }?.asString,
            token = ownerJsonObject["token"]?.takeIf { it.isJsonNull.not() }?.asString,
            analyticsId = ownerJsonObject["analyticsId"]?.takeIf { it.isJsonNull.not() }?.asString,
            tokenType = ownerJsonObject["tokenType"]?.takeIf { it.isJsonNull.not() }?.asString,
            paymentInstrumentType = ownerJsonObject["paymentInstrumentType"]?.takeIf { it.isJsonNull.not() }?.asString,
            paymentInstrumentData = paymentInstrumentDataJson?.takeIf { it.isJsonNull.not() }
                ?.toPaypalData(),
            threeDSecureAuthentication = threeDSecureAuthenticationJson?.takeIf { it.isJsonNull.not() }
                ?.to3DS()
        )
    }
}

private fun JsonObject.to3DS(): ThreeDSecureAuthentication {
    return ThreeDSecureAuthentication(
        responseCode = get("responseCode")?.takeIf { it.isJsonNull.not() }?.asString,
        reasonCode = get("reasonCode")?.takeIf { it.isJsonNull.not() }?.asString,
        reasonText = get("reasonText")?.takeIf { it.isJsonNull.not() }?.asString,
        protocolVersion = get("protocolVersion")?.takeIf { it.isJsonNull.not() }?.asString,
        challengeIssued = get("challengeIssued")?.takeIf { it.isJsonNull.not() }?.asString
    )
}

private fun JsonObject.toPaypalData(): PaypalData {
    return PaypalData(
        paypalOrderId = this["paypalOrderId"]?.takeIf { it.isJsonNull.not() }?.asString,
        externalPayerInfo = this["externalPayerInfo"]?.takeIf { it.isJsonNull.not() }?.asString,
        paypalStatus = this["paypalStatus"]?.takeIf { it.isJsonNull.not() }?.asString
    )
}

private fun JsonObject.toCardData(): CardData {
    return CardData(
        last4Digits = get("last4Digits")?.takeIf { it.isJsonNull.not() }?.asString,
        expirationMonth = get("expirationMonth")?.takeIf { it.isJsonNull.not() }?.asString,
        expirationYear = get("expirationYear")?.takeIf { it.isJsonNull.not() }?.asString,
        cardholderName = get("cardholderName")?.takeIf { it.isJsonNull.not() }?.asString,
        network = get("network")?.takeIf { it.isJsonNull.not() }?.asString,
        isNetworkTokenized = get("isNetworkTokenized")?.takeIf { it.isJsonNull.not() }?.asBoolean
            ?: false,
        binData = get("binData").asJsonObject.toBinData()
    )
}

private fun JsonObject.toBinData(): CardBinData {
    return CardBinData(
        network = get("network")?.takeIf { it.isJsonNull.not() }?.asString,
        issuerCountryCode = get("issuerCountryCode")?.takeIf { it.isJsonNull.not() }?.asString,
        issuerName = get("issuerName")?.takeIf { it.isJsonNull.not() }?.asString,
        issuerCurrencyCode = get("issuerCurrencyCode")?.takeIf { it.isJsonNull.not() }?.asString,
        regionalRestriction = get("regionalRestriction")?.takeIf { it.isJsonNull.not() }?.asString,
        accountNumberType = get("accountNumberType")?.takeIf { it.isJsonNull.not() }?.asString,
        accountFundingType = get("accountFundingType")?.takeIf { it.isJsonNull.not() }?.asString,
        prepaidReloadableIndicator = get("prepaidReloadableIndicator")?.takeIf { it.isJsonNull.not() }?.asString,
        productUsageType = get("productUsageType")?.takeIf { it.isJsonNull.not() }?.asString,
        productCode = get("productCode")?.takeIf { it.isJsonNull.not() }?.asString,
        productName = get("productName")?.takeIf { it.isJsonNull.not() }?.asString,
    )
}
