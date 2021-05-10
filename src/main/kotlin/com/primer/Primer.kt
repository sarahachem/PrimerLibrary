package com.primer

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

const val PRIMER_URL_API = "https://sdk.api.staging.primer.io"

interface PrimerApi {
    @POST("/payment-instruments")
    @Headers("Content-Type: application/json")
    fun addPaymentInstrument(@Body paymentInstrument: PaymentInstrument): Call<TokenizedPaymentInstrumentInfo>
}

open class TokenizedPaymentInstrumentInfo(
    var deleted: Boolean? = false,
    var createdAt: String? = null,
    var updatedAt: String? = null,
    var deletedAt: String? = null,
    var token: String? = null,
    var analyticsId: String? = null,
    var tokenType: String? = null,
    var paymentInstrumentType: String? = null,
    var vaultData: String? = null,
    var threeDSecureAuthentication: ThreeDSecureAuthentication? = null
)

class TokenizedCardInfo(
    deleted: Boolean? = false,
    createdAt: String? = null,
    updatedAt: String? = null,
    deletedAt: String? = null,
    token: String? = null,
    analyticsId: String? = null,
    tokenType: String? = null,
    paymentInstrumentType: String? = null,
    vaultData: String? = null,
    threeDSecureAuthentication: ThreeDSecureAuthentication? = null,
    @SerializedName("paymentInstrumentData")
    var paymentInstrumentData: CardData
) : TokenizedPaymentInstrumentInfo(
    deleted,
    createdAt,
    updatedAt,
    deletedAt,
    token,
    analyticsId, tokenType,
    paymentInstrumentType,
    vaultData,
    threeDSecureAuthentication
)

data class ThreeDSecureAuthentication(
    var responseCode: String? = null,
    var reasonCode: String? = null,
    var reasonText: String? = null,
    var protocolVersion: String? = null,
    var challengeIssued: String? = null
)

class TokenizedPaypalInfo(
    deleted: Boolean = false,
    createdAt: String? = null,
    updatedAt: String? = null,
    deletedAt: String? = null,
    token: String? = null,
    analyticsId: String? = null,
    tokenType: String? = null,
    paymentInstrumentType: String? = null,
    vaultData: String? = null,
    threeDSecureAuthentication: ThreeDSecureAuthentication? = null,
    @SerializedName("paymentInstrumentData")
    var paymentInstrumentData: PaypalData? = null
) : TokenizedPaymentInstrumentInfo(
    deleted,
    createdAt,
    updatedAt,
    deletedAt,
    token,
    analyticsId, tokenType,
    paymentInstrumentType,
    vaultData,
    threeDSecureAuthentication
)

data class CardData(
    var last4Digits: String? = null,
    var expirationMonth: String? = null,
    var expirationYear: String? = null,
    var cardholderName: String? = null,
    var network: String? = null,
    var isNetworkTokenized: Boolean? = false,
    var binData: CardBinData? = null
)

data class CardBinData(
    var network: String? = null,
    var issuerCountryCode: String? = null,
    var issuerName: String? = null,
    var issuerCurrencyCode: String? = null,
    var regionalRestriction: String? = null,
    var accountNumberType: String? = null,
    var accountFundingType: String? = null,
    var prepaidReloadableIndicator: String? = null,
    var productUsageType: String? = null,
    var productCode: String? = null,
    var productName: String? = null
)

data class PaypalData(
    var paypalOrderId: String? = null,
    var externalPayerInfo: String? = null,
    var paypalStatus: String? = null
)

open class PaymentInstrument(
    @SerializedName("paymentInstrument")
    var instrument: Instrument
)

@Parcelize
open class Instrument : Parcelable

@Parcelize
data class Card(
    var number: String,
    var cvv: String,
    var expirationMonth: String,
    var expirationYear: String,
    @SerializedName("cardholderName")
    var cardHolderName: String
) : Instrument()

@Parcelize
data class Paypal(
    var paypalOrderId: String
) : Instrument()

class PrimerAuthInterceptor(private var token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("Primer-Client-Token", TokenUtils.convertToToken(token).accessToken)
                .build()
        )
    }
}

