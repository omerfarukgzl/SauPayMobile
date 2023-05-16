package com.example.saupay.yedek

import com.example.saupay.model.transaction.TransactionResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TransactionDaoInterface {

    @GET("getTransactionMerchantByUserId/{userId}")
    fun getTransactionByUserId(@Path("userId") userId: String): retrofit2.Call<TransactionResponse>

}