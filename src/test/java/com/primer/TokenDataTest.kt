package com.primer

import org.junit.Assert
import org.junit.Test

class TokenDataTest {
    private val clientTokenString =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2Nlc3NUb2tlbiI6ImYwOTZkNjMzLTM1YTgtNGE2YS05Zjg1LWQ4MWRlMTc4ZDJlNSIsImFuYWx5dGljc1VybCI6Imh0dHBzOi8vYW5hbHl0aWNzLmFwaS5zdGFnaW5nLmNvcmUucHJpbWVyLmlvL21peHBhbmVsIiwiaW50ZW50IjoiQ0hFQ0tPVVQiLCJjb25maWd1cmF0aW9uVXJsIjoiaHR0cHM6Ly9hcGkuc3RhZ2luZy5wcmltZXIuaW8vY2xpZW50LXNkay9jb25maWd1cmF0aW9uIiwiY29yZVVybCI6Imh0dHBzOi8vYXBpLnN0YWdpbmcucHJpbWVyLmlvIiwicGNpVXJsIjoiaHR0cHM6Ly9zZGsuYXBpLnN0YWdpbmcucHJpbWVyLmlvIiwiZW52IjoiU1RBR0lORyIsInRocmVlRFNlY3VyZUluaXRVcmwiOiJodHRwczovL3NvbmdiaXJkc3RhZy5jYXJkaW5hbGNvbW1lcmNlLmNvbS9jYXJkaW5hbGNydWlzZS92MS9zb25nYmlyZC5qcyIsInRocmVlRFNlY3VyZVRva2VuIjoiZXlKMGVYQWlPaUpLVjFRaUxDSmhiR2NpT2lKSVV6STFOaUo5LmV5SnFkR2tpT2lJNVkyVm1ZMlZqTXkxbU5UUXhMVFEzTTJFdFlqRTNZeTA0WkdOak0yWTVOV1E0TlRVaUxDSnBZWFFpT2pFMk1qQXhNakEwTmpVc0ltbHpjeUk2SWpWbFlqVmlZV1ZqWlRabFl6Y3lObVZoTldaaVlUZGxOU0lzSWs5eVoxVnVhWFJKWkNJNklqVmxZalZpWVRReFpEUTRabUprTmpBNE9EaGlPR1UwTkNKOS52alNNUmNiTFZKbG9mckZsQ0puUlppdkc5NVprc1hqcmVnSk1JcGYtaFpzIiwicGF5bWVudEZsb3ciOiJERUZBVUxUIn0.Amc9FqCmgpJmYLn-j_h9CzttPQ7mb3JaaSMQVo1QUSs"

    private val clientTokenData = TokenData(
        accessToken = "f096d633-35a8-4a6a-9f85-d81de178d2e5",
        analyticsUrl = "https://analytics.api.staging.core.primer.io/mixpanel",
        intent = "CHECKOUT",
        configurationUrl = "https://api.staging.primer.io/client-sdk/configuration",
        coreUrl = "https://api.staging.primer.io",
        pciUrl = "https://sdk.api.staging.primer.io",
        env = "STAGING",
        threeDSecureInitUrl = "https://songbirdstag.cardinalcommerce.com/cardinalcruise/v1/songbird.js",
        threeDSecureToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5Y2VmY2VjMy1mNTQxLTQ3M2EtYjE3Yy04ZGNjM2Y5NWQ4NTUiLCJpYXQiOjE2MjAxMjA0NjUsImlzcyI6IjVlYjViYWVjZTZlYzcyNmVhNWZiYTdlNSIsIk9yZ1VuaXRJZCI6IjVlYjViYTQxZDQ4ZmJkNjA4ODhiOGU0NCJ9.vjSMRcbLVJlofrFlCJnRZivG95ZksXjregJMIpf-hZs",
        paymentFlow = "DEFAULT"
    )

    @Test
    fun getAccessTokenFromClientToken() {
        val convertedToken = TokenUtils.convertToToken(clientTokenString)
        Assert.assertEquals(clientTokenData.accessToken, convertedToken.accessToken)
        Assert.assertEquals(clientTokenData.analyticsUrl, convertedToken.analyticsUrl)
        Assert.assertEquals(clientTokenData.configurationUrl, convertedToken.configurationUrl)
        Assert.assertEquals(clientTokenData.coreUrl, convertedToken.coreUrl)
        Assert.assertEquals(clientTokenData.env, convertedToken.env)
        Assert.assertEquals(clientTokenData.intent, convertedToken.intent)
        Assert.assertEquals(clientTokenData.paymentFlow, convertedToken.paymentFlow)
        Assert.assertEquals(clientTokenData.pciUrl, convertedToken.pciUrl)
        Assert.assertEquals(clientTokenData.threeDSecureInitUrl, convertedToken.threeDSecureInitUrl)
        Assert.assertEquals(clientTokenData.threeDSecureToken, convertedToken.threeDSecureToken)
    }
}
