package com.example.saupay.model.treedSecure

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class TreeDSecure (
    @SerializedName("merchantName")
    @Expose
    val merchantName: String?,
    @SerializedName("amount")
    @Expose
    val amount: BigDecimal?,
    @SerializedName("date")
    @Expose
    val date: String?,
    @SerializedName("cardNumber")
    @Expose
    val cardNumber:String?,
    @SerializedName("userPhone")
    @Expose
    val userPhone:String?,
){
}