package com.example.saupaymobile

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @SerializedName("data")
    @Expose
    val data: Data,
    @SerializedName("success")
    @Expose
    val success: Boolean,
    @SerializedName("errorDescription")
    @Expose
    val errorDescription: String,
    @SerializedName("errorCode")
    @Expose
    val errorCode: String
    ) {
}