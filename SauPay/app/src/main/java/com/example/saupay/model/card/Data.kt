package com.example.saupay.model.card

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Data (
    @SerializedName("cards") //
    @Expose
    val cards: List<Card>?){

}