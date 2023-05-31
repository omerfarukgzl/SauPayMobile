package com.example.saupay.ui.cards

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.saugetir.data.remote.model.request.EncryptedRequest
import com.example.saupay.data.remote.card.CardRepository
import com.example.saupay.data.remote.card.RetrofitClientCard
import com.example.saupay.data.remote.card.response.CardResponse
import com.example.saupay.databinding.FragmentCardListBinding
import com.example.saupay.ui.home.MainActivity
import com.example.saupay.ui.payment.choosecard.ChooseCardFragmentArgs
import com.example.saupay.ui.payment.verification.PaymentActivty
import com.example.saupay.utils.EncryptionUtil
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CardListFragment : Fragment() {

    private var _binding: FragmentCardListBinding?=null
    private val binding get() = _binding!!
/*    private var paymentToken : String?= null
    private var loginResponse : LoginResponse?= null*/

    private lateinit var viewPager: ViewPager2
    private lateinit var viewAdapter: CardsRecyclerAdapter
    private lateinit var viewManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentCardListBinding.inflate(inflater, container, false)

        viewManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val loginResponse= (activity as MainActivity).getSessionToken()!!
        val email = (activity as MainActivity).getEmail()!!
        getCardByUserEmail(loginResponse, email!!)
        return binding.root
    }


    fun getCardByUserEmail(sessionToken:String,email:String)
    {
        try {
            var encryptedPaymentRequest = EncryptedRequest()
            val paramObject = JSONObject()
            paramObject.put("email", email)
            Log.d("paramObject", paramObject.toString() )

            encryptedPaymentRequest.data = EncryptionUtil.encrypt(paramObject.toString())

            Log.d("encryptedPaymentRequest", encryptedPaymentRequest.toString())

            RetrofitClientCard.setBearerToken(sessionToken)
            val repository = CardRepository(RetrofitClientCard.getUserCards())
            val call = repository.getUserCards(encryptedPaymentRequest)

            Log.d("BenGeldimCard", sessionToken)
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

                            viewAdapter = CardsRecyclerAdapter(cardResponse.data!!.cards!!)
                            viewPager = binding.viewPager22.apply {
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

                        viewAdapter = CardsRecyclerAdapter(listOf())
                        viewPager = binding.viewPager22.apply {
                            adapter = viewAdapter
                        }
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