package com.primer

import com.google.gson.GsonBuilder
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import java.io.FileReader

class PrimerApiResponsesTest {
    private lateinit var tokenizedPaypalInstrumentInfo: TokenizedPaypalInfo
    private lateinit var tokenizedCardInstrumentInfo: TokenizedCardInfo
    private val gsonBuilder = GsonBuilder().registerTypeAdapter(
        TokenizedPaymentInstrumentInfo::class.java,
        TokenizedPaymentDeserializer()
    ).create()

    @Before
    fun setup() {
        tokenizedPaypalInstrumentInfo = TokenizedPaypalInfo(
            token = "RSY62p4GREqdc_G9Bpmk1HwxNjIwNTYzOTk4",
            tokenType = "SINGLE_USE",
            paymentInstrumentType = "PAYPAL_ORDER",
            analyticsId = "3jV0x4adWdOnQS3yZlXlfXVS",
            paymentInstrumentData = PaypalData(
                paypalOrderId = "1"
            ),
            threeDSecureAuthentication = ThreeDSecureAuthentication(
                responseCode = "NOT_PERFORMED"
            )
        )

        tokenizedCardInstrumentInfo = TokenizedCardInfo(
            token = "3w-hNunURGKFXc9NLzcBenwxNjIwNDE0NTEy",
            tokenType = "SINGLE_USE",
            paymentInstrumentType = "PAYMENT_CARD",
            analyticsId = "-NxZbWYcXPiRMynOCdzu2G5L",
            paymentInstrumentData = CardData(
                last4Digits = "1111",
                expirationMonth = "03",
                expirationYear = "2030",
                cardholderName = "J Doe",
                network = "Visa",
                isNetworkTokenized = false,
                binData = CardBinData(
                    network = "VISA",
                    issuerCountryCode = "US",
                    issuerName = "JPMORGAN CHASE BANK, N.A.",
                    regionalRestriction = "UNKNOWN",
                    accountNumberType = "UNKNOWN",
                    accountFundingType = "UNKNOWN",
                    prepaidReloadableIndicator = "NOT_APPLICABLE",
                    productUsageType = "UNKNOWN",
                    productCode = "VISA",
                    productName = "VISA"
                )
            )
        )
    }

    @Test
    fun getPaypalPaymentResponse() {
        val paypalPaymentResponse = gsonBuilder.fromJson(
            FileReader("tokenizedPaypalResponse"),
            TokenizedPaymentInstrumentInfo::class.java
        )
        Assert.assertEquals(paypalPaymentResponse.token, tokenizedPaypalInstrumentInfo.token)
        Assert.assertEquals(
            paypalPaymentResponse.analyticsId,
            tokenizedPaypalInstrumentInfo.analyticsId
        )
        Assert.assertEquals(
            paypalPaymentResponse.tokenType,
            tokenizedPaypalInstrumentInfo.tokenType
        )
        Assert.assertEquals(
            (paypalPaymentResponse as TokenizedPaypalInfo).paymentInstrumentData,
            tokenizedPaypalInstrumentInfo.paymentInstrumentData
        )
        Assert.assertEquals(
            paypalPaymentResponse.threeDSecureAuthentication?.reasonCode,
            tokenizedPaypalInstrumentInfo.threeDSecureAuthentication?.reasonCode
        )
    }

    @Test
    fun getCardPaymentResponse() {
        val cardPaymentResponse = gsonBuilder.fromJson(
            FileReader("tokenizedCardResponse"),
            TokenizedPaymentInstrumentInfo::class.java
        )
        Assert.assertEquals(cardPaymentResponse.token, tokenizedCardInstrumentInfo.token)
        Assert.assertEquals(
            cardPaymentResponse.analyticsId,
            tokenizedCardInstrumentInfo.analyticsId
        )
        Assert.assertEquals(cardPaymentResponse.tokenType, tokenizedCardInstrumentInfo.tokenType)
        Assert.assertEquals(
            (cardPaymentResponse as TokenizedCardInfo).paymentInstrumentData,
            tokenizedCardInstrumentInfo.paymentInstrumentData
        )
    }
}
