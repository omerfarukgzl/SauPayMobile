package com.example.saupay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saupay.databinding.ActivityMainBinding
import com.example.saupaymobile.ApiUtils
import com.example.saupaymobile.TransactionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: TransactionRecyclerAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tdi = ApiUtils.getTransactionDaoInterface()

        tdi.getTransactionByUserId().enqueue(object : Callback<TransactionResponse> {
            //onResponse fonksiyonu, sunucudan dönen cevabı işlemek için kullanılır.
            override fun onResponse(call: Call<TransactionResponse>, response: Response<TransactionResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        Log.d("MainActivity", "isSuccessful")
                        val transactionResponse = response.body()!!
                        viewManager = LinearLayoutManager(this@MainActivity)
                        viewAdapter = TransactionRecyclerAdapter(transactionResponse.data.transactions)



                        recyclerView = binding.recyclerTransactions.apply {
                            // use this setting to improve performance if you know that changes
                            // in content do not change the layout size of the RecyclerView
                            setHasFixedSize(true)

                            // use a linear layout manager
                            layoutManager = viewManager

                            // specify an viewAdapter (see also next example)
                            adapter = viewAdapter
                        }
                        for (transaction in transactionResponse.data.transactions) {
                            Log.d("MainActivity", transaction.toString())
                        }
                    }
                    else if (response.body()!!.success) {
                        Log.d("MainActivity", "isn't Successful")
                        val transactionResponse = response.body()!!
                        Log.d("MainActivity", transactionResponse.toString())
                    }
                }
            }
            // onFailure fonksiyonu, sunucuya istek gönderirken bir hata oluşması durumunda çalışır.
            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                Log.e("MainActivityError", t.message!!)
            }
        })




       // getTransactionByUserId()

    }

/*
    fun getTransactionByUserId() {

        val tdi = ApiUtils.getTransactionDaoInterface()

        tdi.getTransactionByUserId().enqueue(object : Callback<TransactionResponse> {
            //onResponse fonksiyonu, sunucudan dönen cevabı işlemek için kullanılır.
            override fun onResponse(call: Call<TransactionResponse>, response: Response<TransactionResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        Log.d("MainActivity", "isSuccessful")
                        val transactionResponse = response.body()!!
                        for (transaction in transactionResponse.data.transactions) {
                            Log.d("MainActivity", transaction.toString())
                        }
                    }
                    else if (response.body()!!.success) {
                        Log.d("MainActivity", "isn't Successful")
                        val transactionResponse = response.body()!!
                        Log.d("MainActivity", transactionResponse.toString())
                    }
                }
            }
            // onFailure fonksiyonu, sunucuya istek gönderirken bir hata oluşması durumunda çalışır.
            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                Log.e("MainActivityError", t.message!!)
            }
        })

    }*/


}