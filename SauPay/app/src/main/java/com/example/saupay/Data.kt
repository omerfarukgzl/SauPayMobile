package com.example.saupaymobile

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Data (
    @SerializedName("transactions")
    @Expose
    val transactions: List<Transaction>) {
}