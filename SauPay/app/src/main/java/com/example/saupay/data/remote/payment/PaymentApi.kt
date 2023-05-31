package com.example.saupay.data.remote.payment

import com.example.saugetir.data.remote.model.request.EncryptedRequest
import com.example.saupay.data.remote.payment.response.PaymentResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PaymentApi {

    @POST("getTransactionMerchantByToken")
    fun transactionMerchantByTokenRequest(@Body encryptedPaymentRequest: EncryptedRequest): Call<PaymentResponse>
}