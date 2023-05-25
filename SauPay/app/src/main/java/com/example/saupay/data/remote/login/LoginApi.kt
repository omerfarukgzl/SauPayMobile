package com.example.saupay.data.remote.login

import com.example.saupay.data.remote.login.request.LoginRequest
import com.example.saupay.data.remote.login.response.LoginResponse
import com.example.saupay.data.remote.transaction.response.TransactionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginApi {

    @POST("login")
    fun loginRequest(@Body loginRequest: LoginRequest): Call<LoginResponse>

}