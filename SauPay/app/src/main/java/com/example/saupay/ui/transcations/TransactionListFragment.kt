package com.example.saupay.ui.transcations

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saupay.databinding.FragmentTransactionListBinding
import com.example.saupay.model.errorResponse.ErrorResponse
import com.example.saupay.model.transaction.TransactionResponse
import com.example.saupay.ui.MainActivity
import com.example.saupay.yedek.ApiUtils
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TransactionListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: TransactionRecyclerAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var _binding: FragmentTransactionListBinding?=null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTransactionListBinding.inflate(inflater, container, false)
        viewManager = LinearLayoutManager(activity)

        binding.button.setOnClickListener {

            // main activitydeki fonksiyonu çağırıyoruz
            getTransactionsByUserId((activity as MainActivity).getMyData()!!)

            //viewModel.getTransactionsByUserId(binding.userIdText.text.toString())

        }
        return binding.root
    }

    fun getTransactionsByUserId(userId: String)
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

                        viewAdapter = TransactionRecyclerAdapter(transactionResponse.data?.transactions!!)
                        binding.errorMaessage.visibility = View.INVISIBLE
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
                    viewAdapter = TransactionRecyclerAdapter(listOf())

                    recyclerView = binding.recyclerTransactions.apply {
                        setHasFixedSize(true)
                        layoutManager = viewManager
                        adapter = viewAdapter
                    }
                    binding.errorMaessage.visibility = View.VISIBLE
                    binding.errorMaessage.text = transactionResponse.toString()
                }

            }
            // onFailure fonksiyonu, sunucuya istek gönderirken bir hata oluşması durumunda çalışır.
            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                Log.e("MainActivityError", t.message!!)
            }
        })




    }

}