package com.example.saupay.data.remote.treedsecure.response

import com.example.saupay.data.remote.errorResponse.Status
import com.example.saupay.model.paymentBank.PaymentBank
import com.example.saupay.model.treedSecure.TreeDSecure

import com.google.gson.annotations.SerializedName

data class PaymentBankResponse (
    @SerializedName("data")
    val `data`: PaymentBank?,
    @SerializedName("status")
    val status: Status?
    ){
}