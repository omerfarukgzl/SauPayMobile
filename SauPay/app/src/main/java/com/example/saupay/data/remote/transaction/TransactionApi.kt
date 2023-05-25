package com.example.saupay.data.remote.transaction

import com.example.saupay.data.remote.transaction.response.TransactionResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionApi {

    @GET("getTransactionMerchantByUserId/{userId}")
    fun getTransactionByUserId(@Path("userId") userId: String): retrofit2.Call<TransactionResponse>

}