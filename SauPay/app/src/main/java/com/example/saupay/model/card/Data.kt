package com.example.saupay.model.card

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data (
    @SerializedName("cards") //
    @Expose
    val cards: List<Card>?): Serializable {

}