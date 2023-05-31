package com.example.saupay.data.remote.login.request

data class RegisterRequest(
    val userEmail: String,
    val userPassword: String,
    val userName: String,
    val userSurname: String,
    val userPhone: String,
    val userTC: String
) {
}