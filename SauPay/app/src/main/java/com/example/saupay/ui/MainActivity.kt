package com.example.saupay.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.saupay.data.remote.login.response.LoginResponse
import com.example.saupay.databinding.ActivityMainBinding
import com.example.saupay.ui.transactions.TransactionRecyclerAdapter

class MainActivity :  AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: TransactionRecyclerAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var binding: ActivityMainBinding

    private var loginResponse: LoginResponse? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


         loginResponse = intent.extras?.getSerializable("Login_Response") as LoginResponse?

        Log.d("loginResponseTokOMER", loginResponse?.token.toString())
        Log.d("loginResponseRefOMER", loginResponse?.refreshToken.toString())

        if (loginResponse != null) {
            // Objeyi kullanmak için burada işlemler yapın
            Log.d("Omer", "Received object from MainActivity: $loginResponse")
        }






    }

    fun getSessionToken(): String? {
        return loginResponse!!.token.toString()
    }








}





















/*fun getTransactionsByUserId(userId: String)
    {
        val tdi = ApiUtils.getTransactionDaoInterface()

        tdi.getTransactionByUserId(userId).enqueue(object : Callback<TransactionResponse> {
            //onResponse fonksiyonu, sunucudan dönen cevabı işlemek için kullanılır.
            override fun onResponse(call: Call<TransactionResponse>, response: Response<TransactionResponse>) {
                Log.d("MainActivity", response.body().toString())
                if (response.isSuccessful) {

                    Log.d("MainActivity", response.body().toString())
                    Log.d("MainActivity", "istek geldi ve başarılı !!!")
                    if (response.body()!!.status?.success!!){
                        Log.d("MainActivity", "isSuccessful")
                        val transactionResponse = response.body()!!

                        viewManager = LinearLayoutManager(this@MainActivity)
                        viewAdapter = TransactionRecyclerAdapter(transactionResponse.data?.transactions!!)

                        recyclerView = binding.recyclerTransactions.apply {
                            setHasFixedSize(true)
                            layoutManager = viewManager
                            adapter = viewAdapter
                        }
                        for (transaction in transactionResponse.data?.transactions!!) {
                            Log.d("MainActivity", transaction.toString())
                        }
                    }

                }
                else {
                    val errorBody = response.errorBody()?.string()

                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)

                    val transactionResponse = errorResponse.status?.errorDescription
                    // Burada, errorResponse özel hata cevabını içerecektir.

                    Log.d("MainActivity",errorBody.toString())
                    Log.d("MainActivity", "isn't Successful")
                    Log.d("MainActivity", transactionResponse.toString())
                    binding.errorMaessage.text = transactionResponse.toString()
                }

            }
            // onFailure fonksiyonu, sunucuya istek gönderirken bir hata oluşması durumunda çalışır.
            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                Log.e("MainActivityError", t.message!!)
            }
        })




    }*/









/*    fun getTransactionsByUserId(userId: String)
    {
/*

        val tdi = ApiUtils.getTransactionDaoInterface()

        tdi.getTransactionByUserId(userId).enqueue(object : Callback<TransactionResponse> {
            //onResponse fonksiyonu, sunucudan dönen cevabı işlemek için kullanılır.
            override fun onResponse(call: Call<TransactionResponse>, response: Response<TransactionResponse>) {
                if (response.isSuccessful) {
                    Log.d("MainActivity", "istek geldi ve başarılı !!!")
                    if (response.body()!!.success) {
                        Log.d("MainActivity", "isSuccessful")
                        val transactionResponse = response.body()!!


                        viewManager = LinearLayoutManager(this@MainActivity)
                        viewAdapter = TransactionRecyclerAdapter(transactionResponse.transactions!!)



                        recyclerView = binding.recyclerTransactions.apply {
                            // use this setting to improve performance if you know that changes
                            // in content do not change the layout size of the RecyclerView
                            setHasFixedSize(true)

                            // use a linear layout manager
                            layoutManager = viewManager

                            // specify an viewAdapter (see also next example)
                            adapter = viewAdapter
                        }
                        for (transaction in transactionResponse.transactions!!) {
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
*/


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
*/



















































