package com.example.saupay.model.card

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Card (
    @SerializedName("cardNumber") 
    @Expose
    val cardNumber:String? = null,
    @SerializedName("binNumber")
    @Expose
    val binNumber:Integer? = null,
    @SerializedName("cardHolderName")
    @Expose
    val cardHolderName:String? = null,
    @SerializedName("cardCvv")
    @Expose
    val cardCvv:String? = null,
    @SerializedName("cardExpireDate")
    @Expose
    val cardExpireDate:String? = null,
    @SerializedName("cardType")
    @Expose
    val cardType:String? = null,
    @SerializedName("bankName")
    @Expose
    val bankName:String? = null
): Serializable {

}