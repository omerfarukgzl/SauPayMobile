package com.example.saupay.data.remote.card

import com.example.saugetir.data.remote.model.request.EncryptedRequest
import com.example.saupay.data.remote.treedsecure.response.PaymentBankResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PaymentBankApi {

    @POST("paymentBank")
    fun paymentBank(@Body encryptedPaymentRequest: EncryptedRequest): Call<PaymentBankResponse>
}