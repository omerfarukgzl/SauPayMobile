package com.example.saupay.ui.payment.choosecard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.saugetir.data.remote.model.request.EncryptedRequest
import com.example.saupay.data.remote.card.CardRepository
import com.example.saupay.data.remote.card.TreeDSecureRepository
import com.example.saupay.data.remote.card.RetrofitClientCard
import com.example.saupay.data.remote.card.RetrofitClientTreeDSecure
import com.example.saupay.data.remote.card.response.CardResponse
import com.example.saupay.data.remote.treedsecure.response.TreeDSecureResponse
import com.example.saupay.databinding.FragmentChooseCardBinding
import com.example.saupay.model.treedSecure.TreeDSecure
import com.example.saupay.ui.payment.verification.PaymentActivty
import com.example.saupay.utils.EncryptionUtil
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChooseCardFragment : Fragment() {
    private var _binding: FragmentChooseCardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
/*    private var paymentToken : String?= null
    private var loginResponse : LoginResponse?= null*/

    private lateinit var viewPager: ViewPager2
    private lateinit var viewAdapter: CardRecyclerAdapter
    private lateinit var viewManager: LinearLayoutManager




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChooseCardBinding.inflate(inflater, container, false)
        //viewManager = LinearLayoutManager(context)
        viewManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val bundle: ChooseCardFragmentArgs by navArgs()
        val email = bundle.email

        val loginResponse= (activity as PaymentActivty).getLoginResponse()!!
        val paymentToken = (activity as PaymentActivty).getPaymentToken()!!

        Log.d("ChooseCardLogin", loginResponse?.token.toString())

        getCardByUserEmail(paymentToken!!, loginResponse!!.token.toString(), email!!)

        return binding.root

    }

    fun getCardByUserEmail(paymentToken:String, sessionToken:String,email:String)
    {
        try {
            var encryptedPaymentRequest = EncryptedRequest()
            val paramObject = JSONObject()
            paramObject.put("paymentToken", paymentToken)
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

                            viewAdapter = CardRecyclerAdapter(cardResponse.data!!.cards!!)
                            viewPager = binding.viewPager2.apply {
                                adapter = viewAdapter
                            }
                            for (transaction in cardResponse.data?.cards!!) {
                                Log.d("MainActivity", transaction.toString())
                            }

                            binding.chooseCardButton.setOnClickListener{

                                var currentPosition = viewPager.currentItem
                                var card = viewAdapter.getItem(currentPosition)
                                sendChooseCardRequestandGetTreeDSecureResponse(paymentToken,sessionToken, card!!.cardNumber.toString())
                            }

                        }

                    }
                    else {
                        Log.d("MainActivity","else Hatası")
                        Log.d("MainActivity", response.errorBody().toString())

                        viewAdapter = CardRecyclerAdapter(listOf())
                        viewPager = binding.viewPager2.apply {
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

    fun sendChooseCardRequestandGetTreeDSecureResponse(paymentToken:String, sessionToken:String, cardNumber:String){

        try {
            var encryptedPaymentRequest = EncryptedRequest()
            val paramObject = JSONObject()
            paramObject.put("paymentToken", paymentToken)
            paramObject.put("cardNumber", cardNumber)
            Log.d("paramObject", paramObject.toString() )

            encryptedPaymentRequest.data = EncryptionUtil.encrypt(paramObject.toString())

            Log.d("encryptedPaymentRequest", encryptedPaymentRequest.toString())

            RetrofitClientTreeDSecure.setBearerToken(sessionToken)
            val repository = TreeDSecureRepository(RetrofitClientTreeDSecure.getUserCards())
            val call = repository.getUserCards(encryptedPaymentRequest)

            Log.d("BenGeldimCard", sessionToken)
            call.enqueue(object : Callback<TreeDSecureResponse> {
                //onResponse fonksiyonu, sunucudan dönen cevabı işlemek için kullanılır.
                override fun onResponse(call: Call<TreeDSecureResponse>, response: Response<TreeDSecureResponse>) {
                    Log.d("MainActivity", response.body().toString())
                    if (response.isSuccessful) {

                        Log.d("MainActivity", response.body().toString())
                        Log.d("MainActivity", "istek geldi ve başarılı !!!")
                        if (response.body()!= null){
                            Log.d("MainActivity", "isSuccessful")
                            val treeDSecureResponse = response.body()!!
                            Log.d("treeDSecureResponse", treeDSecureResponse.toString())

                            val action = ChooseCardFragmentDirections.
                            actionChooseCardFragmentToTreeDSecureFragment(
                                treeDSecureResponse!!.data!!.merchantName!!,
                                treeDSecureResponse!!.data!!.amount!!.toString(),
                                treeDSecureResponse!!.data!!.date!!,
                                treeDSecureResponse!!.data!!.date!!,
                                treeDSecureResponse!!.data!!.cardNumber!!,
                                treeDSecureResponse!!.data!!.userPhone!!,
                            )
                            findNavController().navigate(action)


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
                override fun onFailure(call: Call<TreeDSecureResponse>, t: Throwable) {
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    Log.e("MainActivityError", t.message!!)
                }
            })


        } catch (e: JSONException) {
            e.printStackTrace()
        }



    }

}