package com.example.saupay.model.transaction

import com.example.saupay.model.errorResponse.Status
import com.example.saupay.model.transaction.Transaction
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: Status?) {

}