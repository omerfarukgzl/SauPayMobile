package com.example.saupay.model.paymentBank

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PaymentBank(
    @SerializedName("success")
    @Expose
    val success: Boolean?
) {
}