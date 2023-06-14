package com.example.saupay.ui.transactions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saugetir.data.remote.model.request.EncryptedTokenRequest
import com.example.saupay.data.remote.errorResponse.ErrorResponse
import com.example.saupay.data.remote.transaction.RetrofitClientTransaction
import com.example.saupay.data.remote.transaction.TransactionRepository
import com.example.saupay.databinding.FragmentTransactionListBinding
import com.example.saupay.data.remote.transaction.response.TransactionResponse
import com.example.saupay.ui.home.MainActivity
import com.example.saupay.ui.payment.verification.PaymentActivty
import com.google.gson.Gson
import org.json.JSONException
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

        Log.d("Omer2","Ben Omer")
        Log.d("OmerOmer", (activity as MainActivity).getSessionToken()!!)
        val email = (activity as MainActivity).getEmail()!!
        val sessionToken = (activity as MainActivity).getSessionToken()!!
        getTransactionsByUserEmail(email, sessionToken)
        return binding.root
    }

    fun getTransactionsByUserEmail(email:String, sessionToken:String)
    {
        try {
            var encryptedTokenRequest = EncryptedTokenRequest()


            //encryptedTokenRequest.data = EncryptionUtil.encrypt(paramObject.toString())

            RetrofitClientTransaction.setBearerToken(sessionToken)
            val repository = TransactionRepository(RetrofitClientTransaction.getTransaction())
            val call = repository.requestInitToken(email)

            call.enqueue(object : Callback<TransactionResponse> {
                //onResponse fonksiyonu, sunucudan dönen cevabı işlemek için kullanılır.
                override fun onResponse(call: Call<TransactionResponse>, response: Response<TransactionResponse>) {
                    Log.d("MainActivity", response.body().toString())
                    if (response.isSuccessful) {

                        Log.d("MainActivity", response.body().toString())
                        Log.d("MainActivity", "istek geldi ve başarılı !!!")
                        if (response.body()!= null){
                            Log.d("MainActivity", "isSuccessful")
                            val transactionResponse = response.body()!!
                            Log.d("transactionResponse", transactionResponse.toString())
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
/*                        val errorBody = response.errorBody()?.string()

                        val gson = Gson()
                        val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)

                        val transactionResponse = errorResponse.status?.errorDescription
                        // Burada, errorResponse özel hata cevabını içerecektir.

                        Log.d("MainActivity",errorBody.toString())
                        Log.d("MainActivity", "isn't Successful")
                        Log.d("MainActivity", transactionResponse.toString())*/
                        viewAdapter = TransactionRecyclerAdapter(listOf())

                        recyclerView = binding.recyclerTransactions.apply {
                            setHasFixedSize(true)
                            layoutManager = viewManager
                            adapter = viewAdapter
                        }
                        binding.errorMaessage.visibility = View.VISIBLE
                        //binding.errorMaessage.text = transactionResponse.toString()
                    }

                }
                // onFailure fonksiyonu, sunucuya istek gönderirken bir hata oluşması durumunda çalışır.
                override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    Log.e("TransactionLstFragmentE", t.message!!)
                }
            })


        } catch (e: JSONException) {
            e.printStackTrace()
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




    }*/

}