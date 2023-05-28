package com.example.saupay.data.remote.payment

import com.example.saugetir.data.remote.model.request.EncryptedRequest
import com.example.saupay.data.remote.payment.response.PaymentResponse
import retrofit2.Call

class PaymentRepository(private var paymentApi: PaymentApi)  {

     fun getPaymentInfo(paymentRequest: EncryptedRequest): Call<PaymentResponse> {
        return paymentApi.transactionMerchantByTokenRequest(paymentRequest)
     }

}