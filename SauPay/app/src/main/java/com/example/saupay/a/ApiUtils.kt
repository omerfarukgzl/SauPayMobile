package com.example.saupay.yedek

import com.example.saupay.utils.Constants.BASE_URL

class ApiUtils {

    companion object {

        fun getTransactionDaoInterface(): TransactionDaoInterface {
            return RetrofitClient.getClient(BASE_URL).create(TransactionDaoInterface::class.java)
        }
    }
}