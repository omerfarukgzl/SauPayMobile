package com.example.saupaymobile

class ApiUtils {

    companion object {

        val BASE_URL = "http://10.0.2.2:8888/v1/saupay/"

        fun getTransactionDaoInterface(): TransactionDaoInterface {
            return RetrofitClient.getClient(BASE_URL).create(TransactionDaoInterface::class.java)
        }
    }
}