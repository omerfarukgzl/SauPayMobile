package com.example.saupaymobile

import retrofit2.http.GET

interface TransactionDaoInterface {

    @GET("getTransactionMerchantByUserId/886ac17f-b396-4374-aa8d-25931a25b8f7")
    fun getTransactionByUserId(): retrofit2.Call<TransactionResponse>

}