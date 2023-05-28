package com.example.saupay.data.remote.treedsecure.response

import com.example.saupay.data.remote.errorResponse.Status
import com.example.saupay.model.treedSecure.TreeDSecure

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class TreeDSecureResponse (
    @SerializedName("data")
    val `data`: TreeDSecure?,
    @SerializedName("status")
    val status: Status?
    ){
}