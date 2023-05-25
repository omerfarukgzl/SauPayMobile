package com.example.saupay.data.remote.card

import com.example.saugetir.data.remote.model.request.EncryptedPaymentRequest
import com.example.saupay.data.remote.card.response.CardResponse
import com.example.saupay.data.remote.payment.response.PaymentResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CardApi {

    @POST("getCardsBankByUserEmail")
    fun requestCardsBankByUserEmail(@Body encryptedPaymentRequest: EncryptedPaymentRequest): Call<CardResponse>

}