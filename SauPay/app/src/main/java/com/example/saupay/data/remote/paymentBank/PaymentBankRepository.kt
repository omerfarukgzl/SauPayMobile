package com.example.saupay.data.remote.card

import com.example.saugetir.data.remote.model.request.EncryptedRequest
import com.example.saupay.data.remote.treedsecure.response.PaymentBankResponse
import retrofit2.Call

class PaymentBankRepository(private var paymentBankApi: PaymentBankApi)  {

     fun paymentBank(cardRequest: EncryptedRequest): Call<PaymentBankResponse> {
        return paymentBankApi.paymentBank(cardRequest)
     }

}