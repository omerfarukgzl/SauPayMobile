package com.example.saupay.ui.payment.verification

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.saupay.R
import com.example.saupay.data.remote.login.response.LoginResponse


class PaymentActivty : AppCompatActivity() {
    private var loginResponse: LoginResponse? = null
    private var paymentToken : String?= null
    private var email : String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        Log.d("ActivtyPayment", "paymentActivtyGeldim")


        loginResponse = intent.extras?.getSerializable("Login_Response") as LoginResponse?
        paymentToken = intent.extras?.getString("Payment_Token")
        email = intent.extras?.getString("Email")


        Log.d("loginResponseTokOMER", loginResponse?.token.toString())
        Log.d("loginResponseRefOMER", loginResponse?.refreshToken.toString())

        Log.d("paymentTokenOMER", paymentToken.toString())


        if (loginResponse != null) {
            // Objeyi kullanmak için burada işlemler yapın
            Log.d("Omer", "Received object from MainActivity: $loginResponse")
        }


    }

    fun getLoginResponse(): LoginResponse? {
        return loginResponse
    }

    fun getPaymentToken(): String? {
        return paymentToken.toString()
    }

    fun getEmail(): String? {
        return email.toString()
    }

}