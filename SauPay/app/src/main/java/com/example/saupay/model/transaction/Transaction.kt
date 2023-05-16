package com.example.saupay.model.transaction

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class Transaction(
    @SerializedName("merchantName") //
    @Expose // Bu annotation ile Gson kütüphanesi bu alanı serileştirmeye dahil eder.
    val merchantName: String,
    @SerializedName("amount")
    @Expose
    val amount: BigDecimal,
    @SerializedName("date")
    @Expose
    val date: String
) {
}