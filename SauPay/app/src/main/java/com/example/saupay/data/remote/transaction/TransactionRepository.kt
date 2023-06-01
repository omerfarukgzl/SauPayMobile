package com.example.saupay.data.remote.transaction

import com.example.saupay.data.remote.transaction.response.TransactionResponse
import retrofit2.Call

class TransactionRepository(private var transactionApi: TransactionApi)  {

     fun requestInitToken(email: String): Call<TransactionResponse> {
       return transactionApi.getTransactionByUserEmail(email)
    }

}

/*class MerchantRepository(private var merchantApi: MerchantApi) {

    fun requestInitToken(request: InitRequest): Call<InitResponse> {
       return merchantApi.requestInitToken(request)
    }
}*/