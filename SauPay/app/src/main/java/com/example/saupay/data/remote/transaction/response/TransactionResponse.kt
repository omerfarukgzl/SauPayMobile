package com.example.saupay.data.remote.transaction.response

import com.example.saupay.data.remote.transaction.errorResponse.Status
import com.example.saupay.model.transaction.Data
import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: Status?) {

}