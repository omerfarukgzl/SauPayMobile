package com.example.saupay.data.remote.payment.response

import com.example.saupay.data.remote.transaction.errorResponse.Status
import com.example.saupay.model.payment.Data
import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: Status?) {

}