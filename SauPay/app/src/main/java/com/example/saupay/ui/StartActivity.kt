package com.example.saupay.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.saupay.R
import com.example.saupay.ui.authentication.AuthenticationActivty

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val intent = intent
        val data: Uri? = intent.data

        if (data != null) {
            val receivedData = data.getQueryParameter("token")
            Log.d("SauGetirdenGelenToken: " , receivedData.toString())

            val intent = Intent(this, AuthenticationActivty::class.java)
            intent.putExtra("Received_Data",receivedData.toString())
            startActivity(intent)


            // Veriyi kullanın
        }
    }
}