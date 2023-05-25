package com.example.saupay.ui.payment.card

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.saugetir.data.remote.model.request.EncryptedPaymentRequest
import com.example.saupay.R
import com.example.saupay.data.remote.card.CardRepository
import com.example.saupay.data.remote.card.RetrofitClientCard
import com.example.saupay.data.remote.card.response.CardResponse
import com.example.saupay.data.remote.login.response.LoginResponse
import com.example.saupay.data.remote.payment.PaymentRepository
import com.example.saupay.data.remote.payment.RetrofitClientPayment
import com.example.saupay.data.remote.payment.response.PaymentResponse
import com.example.saupay.databinding.FragmentChooseCardBinding
import com.example.saupay.ui.payment.PaymentActivty
import com.example.saupay.ui.transactions.TransactionRecyclerAdapter
import com.example.saupay.utils.EncryptionUtil
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class
ChooseCardFragment : Fragment() {
    private var _binding: FragmentChooseCardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
/*    private var paymentToken : String?= null
    private var loginResponse : LoginResponse?= null*/

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: CardRecyclerAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_card, container, false)

        val bundle: ChooseCardFragmentArgs by navArgs()
        val email = bundle.email

        val loginResponse= (activity as PaymentActivty).getLoginResponse()!!
        val paymentToken = (activity as PaymentActivty).getPaymentToken()!!

        Log.d("BenGeldim", loginResponse?.token.toString())

        getCardByUserId(paymentToken!!, loginResponse!!.token.toString(), email!!)

        return binding.root

    }

    fun getCardByUserId(paymentToken:String, sessionToken:String,email:String)
    {
        try {
            var encryptedPaymentRequest = EncryptedPaymentRequest()
            val paramObject = JSONObject()
            paramObject.put("token", paymentToken)
            Log.d("paramObject", paramObject.toString() )

            encryptedPaymentRequest.data = EncryptionUtil.encrypt(paramObject.toString())

            Log.d("encryptedPaymentRequest", encryptedPaymentRequest.toString())

            RetrofitClientCard.setBearerToken(sessionToken)
            val repository = CardRepository(RetrofitClientCard.getUserCards())
            val call = repository.getUserCards(encryptedPaymentRequest)

            Log.d("BenGeldim2", sessionToken)
            call.enqueue(object : Callback<CardResponse> {
                //onResponse fonksiyonu, sunucudan dönen cevabı işlemek için kullanılır.
                override fun onResponse(call: Call<CardResponse>, response: Response<CardResponse>) {
                    Log.d("MainActivity", response.body().toString())
                    if (response.isSuccessful) {

                        Log.d("MainActivity", response.body().toString())
                        Log.d("MainActivity", "istek geldi ve başarılı !!!")
                        if (response.body()!= null){
                            Log.d("MainActivity", "isSuccessful")
                            val cardResponse = response.body()!!
                            Log.d("transactionResponse", cardResponse.toString())

                            viewAdapter = CardRecyclerAdapter(cardResponse.data!!.cards!!)
                            recyclerView = binding.recyclerView.apply {
                                setHasFixedSize(true)
                                layoutManager = viewManager
                                adapter = viewAdapter
                            }
                            for (transaction in cardResponse.data?.cards!!) {
                                Log.d("MainActivity", transaction.toString())
                            }
                        }

                    }
                    else {
                        Log.d("MainActivity","else Hatası")
                        Log.d("MainActivity", response.errorBody().toString())
/*                        val errorBody = response.errorBody()?.string()

                        val gson = Gson()
                        val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)

                        val transactionResponse = errorResponse.status?.errorDescription
                        // Burada, errorResponse özel hata cevabını içerecektir.

                        Log.d("MainActivity",errorBody.toString())
                        Log.d("MainActivity", "isn't Successful")
                        Log.d("MainActivity", transactionResponse.toString())*/

                    }

                }
                // onFailure fonksiyonu, sunucuya istek gönderirken bir hata oluşması durumunda çalışır.
                override fun onFailure(call: Call<CardResponse>, t: Throwable) {
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    Log.e("MainActivityError", t.message!!)
                }
            })


        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

}