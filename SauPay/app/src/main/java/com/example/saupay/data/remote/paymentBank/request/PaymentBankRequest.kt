package com.example.saupay.data.remote.treedsecure.request

data class PaymentBankRequest (
    val paymentToken: String?,
    val cardNumber: String?
    ){
}