package com.example.saupay.data.remote.card

import com.example.saugetir.data.remote.model.request.EncryptedRequest
import com.example.saupay.data.remote.treedsecure.response.PaymentBankResponse
import com.example.saupay.data.remote.treedsecure.response.TreeDSecureResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TreeDSecureApi {

    @POST("paymentCompleteRequestForTreeDSecure")
    fun paymentCompleteRequestForTreeDSecure(@Body encryptedPaymentRequest: EncryptedRequest): Call<TreeDSecureResponse>
}