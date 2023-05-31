package com.example.saupay.data.remote.card.response

import com.example.saupay.data.remote.errorResponse.Status
import com.example.saupay.model.card.AddCard
import com.example.saupay.model.card.Data
import com.google.gson.annotations.SerializedName

data class AddCardResponse(
    @SerializedName("data")
    val `data`: AddCard?,
    @SerializedName("status")
    val status: Status?) {
}