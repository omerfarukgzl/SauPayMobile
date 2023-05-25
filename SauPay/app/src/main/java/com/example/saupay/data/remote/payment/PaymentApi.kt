package com.example.saupay.data.remote.payment

import com.example.saugetir.data.remote.model.request.EncryptedPaymentRequest
import com.example.saugetir.data.remote.model.request.EncryptedTokenRequest
import com.example.saupay.data.remote.login.request.LoginRequest
import com.example.saupay.data.remote.login.response.LoginResponse
import com.example.saupay.data.remote.payment.response.PaymentResponse
import com.example.saupay.data.remote.transaction.response.TransactionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentApi {

    @POST("getTransactionMerchantByToken")
    fun loginRequest(@Body encryptedPaymentRequest: EncryptedPaymentRequest): Call<PaymentResponse>

}