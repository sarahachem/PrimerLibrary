This is a simple payment checkout library that allows your mobile app to tokenize payments by calling the Primer API's addPaymentInstrument call and passing the PaymentInstrument as a parameter. The payment instrument can be a card, or a paypal order.

`fun addPaymentInstrument(@Body paymentInstrument: PaymentInstrument): Call<TokenizedPaymentInstrumentInfo>`
For example:

the payment instrument should look like :

{"paymentInstrument" : {"cardholderName":"J Doe","cvv":"737","expirationMonth":"03","expirationYear":"2030","number":"4111111111111111"} }

But to make it simple for you, you just need to create a Card object

Card("J Doe","737","03","2030","4111111111111111") and set it in a PaymentInstrument() and send it to the API. et voilà.
