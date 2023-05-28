package com.example.saupay.data.remote.card.response

import com.example.saupay.data.remote.errorResponse.Status
import com.example.saupay.model.card.Data
import com.google.gson.annotations.SerializedName

data class CardResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: Status?) {

}