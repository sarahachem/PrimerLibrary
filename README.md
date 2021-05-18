This is a sample jitpack library built with kotlin, retrofit2 and compose that allows your mobile app to tokenize payments by calling the Primer API's addPaymentInstrument call and passing the PaymentInstrument as a parameter. The payment instrument can be a card, or a paypal order.

`fun addPaymentInstrument(@Body paymentInstrument: PaymentInstrument): Call<TokenizedPaymentInstrumentInfo>`
For example:

the payment instrument should look like :

{"paymentInstrument" : {"cardholderName":"J Doe","cvv":"737","expirationMonth":"03","expirationYear":"2030","number":"4111111111111111"} }

But to make it simple for you, you just need to create a Card object

Card("J Doe","737","03","2030","4111111111111111") and set it in a PaymentInstrument() and send it to the API. et voilà.
The UI is completely done in compose and for that we added customisable components such as buttons, input fields, linear cells. etc. Every component class has its own preview to show you the outcome and give you an example. Please check it out in the compose package!

To publish a new version of the library, just commit the code, create a new release with a new tag on github, check if the new release is successful on 
https://jitpack.io/#sarahachem/PrimerLibrary and update the tag number in the  app using the library !


The project contains a set of JUnit tests, that can be executed with the following command:

`./gradlew testDebugUnitTest`



