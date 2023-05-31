package com.example.saupay.ui.authentication

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.saupay.R
import com.example.saupay.data.remote.login.response.LoginResponse

class AuthenticationActivty : AppCompatActivity() {
    private var paymentToken: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        Log.d("ActivtyAuthentication ","activtyAutehnticationGeldim")

        paymentToken = intent.extras?.getString("Received_Data")
        Log.d("RecivedDataToken", paymentToken.toString())

/*        val intent = intent
        val data: Uri? = intent.data

        if (data != null) {
            receivedData = data.getQueryParameter("token")
            Log.d("SauGetirdenGelenToken: " , receivedData.toString())
            // Veriyi kullanın



        }*/

    }
    fun getPaymentToken(): String? {
        return paymentToken.toString()
    }
}