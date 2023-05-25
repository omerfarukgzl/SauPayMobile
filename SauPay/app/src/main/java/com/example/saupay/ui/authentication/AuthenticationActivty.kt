package com.example.saupay.ui.authentication

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.saupay.R

class AuthenticationActivty : AppCompatActivity() {
    var receivedData: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        val intent = intent
        val data: Uri? = intent.data

        if (data != null) {
            receivedData = data.getQueryParameter("token")
            Log.d("SauGetirdenGelenToken: " , receivedData.toString())
            // Veriyi kullanın



        }

    }
    fun getPaymentToken(): String? {
        return receivedData.toString()
    }
}