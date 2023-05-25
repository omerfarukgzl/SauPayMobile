package com.example.saupay.data.remote.payment.errorResponse

data class Status (
    val success : Boolean? = null,
    val errorDescription : String? = null,
    val errorCode : String? = null
){
}