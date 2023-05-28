package com.example.saupay.data.remote.payment.response

import com.example.saupay.data.remote.errorResponse.Status
import com.example.saupay.model.payment.Payment
import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("data")
    val payment: Payment?,
    @SerializedName("status")
    val status: Status?) {

}