package com.example.saupay.network

import com.example.saupay.model.transaction.TransactionResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiFactory {


    @GET("getTransactionMerchantByUserId/{userId}")
    suspend fun getTransactionByUserId(@Path("userId") userId: String): retrofit2.Call<TransactionResponse>

/*    @GET("getTransactionMerchantByUserId/{userId}")
    suspend fun getTransactionByUserId(@Path("userId") userId: String): TransactionResponse*/

}