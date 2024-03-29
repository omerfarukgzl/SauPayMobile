package com.example.saupay.data.remote.card

import com.example.saugetir.data.remote.model.request.EncryptedRequest
import com.example.saupay.data.remote.card.response.AddCardResponse
import com.example.saupay.data.remote.card.response.CardResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CardApi {

    @POST("getBankCardsByUserEmailForPayment")
    fun requestCardsBankByUserEmailForPayment(@Body encryptedPaymentRequest: EncryptedRequest): Call<CardResponse>

    @POST("getBankCardsByUserEmail")
    fun requestCardsBankByUserEmail(@Body encryptedPaymentRequest: EncryptedRequest): Call<CardResponse>

    @POST("addCard")
    fun addCard(@Body encryptedPaymentRequest: EncryptedRequest): Call<AddCardResponse>
}