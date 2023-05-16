package com.example.saupay.model.transaction

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Data (
    @SerializedName("transactions") //
    @Expose
    val transactions: List<Transaction>?){

}