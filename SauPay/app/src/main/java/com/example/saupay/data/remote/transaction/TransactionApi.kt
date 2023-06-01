package com.example.saupay.data.remote.transaction

import com.example.saupay.data.remote.transaction.response.TransactionResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionApi {

    @GET("getTransactionMerchantByUserEmail/{email}")
    fun getTransactionByUserEmail(@Path("email") email: String): retrofit2.Call<TransactionResponse>

}