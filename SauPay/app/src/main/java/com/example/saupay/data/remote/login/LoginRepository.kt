package com.example.saupay.data.remote.login

import com.example.saupay.data.remote.login.request.LoginRequest
import com.example.saupay.data.remote.login.request.RegisterRequest
import com.example.saupay.data.remote.login.response.LoginResponse
import com.example.saupay.data.remote.login.response.RegisterResponse
import com.example.saupay.data.remote.transaction.response.TransactionResponse
import retrofit2.Call

class LoginRepository(private var loginApi: LoginApi)  {

     fun loginRequest(loginRequest: LoginRequest): Call<LoginResponse> {
        return loginApi.loginRequest(loginRequest)
     }

        fun registerRequest(registerRequest: RegisterRequest): Call<RegisterResponse> {
            return loginApi.registerRequest(registerRequest)
        }

}