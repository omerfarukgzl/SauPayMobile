package com.example.saupay.ui.payment.verification

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.saugetir.data.remote.model.request.EncryptedRequest
import com.example.saupay.data.remote.card.CardRepository
import com.example.saupay.data.remote.card.RetrofitClientCard
import com.example.saupay.data.remote.card.response.CardResponse
import com.example.saupay.data.remote.login.response.LoginResponse
import com.example.saupay.data.remote.payment.PaymentRepository
import com.example.saupay.data.remote.payment.RetrofitClientPayment
import com.example.saupay.data.remote.payment.response.PaymentResponse
import com.example.saupay.databinding.FragmentPaymentBinding
import com.example.saupay.ui.payment.choosecard.CardRecyclerAdapter
import com.example.saupay.utils.EncryptionUtil
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PaymentFragment : Fragment() {
    private var _binding: FragmentPaymentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var paymentToken : String?= null
    private var loginResponse : LoginResponse?= null
    private var email: String?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        Log.d("FragmentPayment", "fragmentPaymentGeldim")
        loginResponse= (activity as PaymentActivty).getLoginResponse()!!
        paymentToken = (activity as PaymentActivty).getPaymentToken()!!
        email = (activity as PaymentActivty).getEmail()!!

        Log.d("BenGeldim", loginResponse?.token.toString())

        getTransactionByToken(paymentToken!!, loginResponse!!.token.toString(), email!!)

        return binding.root

    }

    fun getTransactionByToken(paymentToken:String, sessionToken:String, email :String)
    {
        try {
            var encryptedPaymentRequest = EncryptedRequest()
            val paramObject = JSONObject()
            paramObject.put("token", paymentToken)
            Log.d("paramObject", paramObject.toString() )

            encryptedPaymentRequest.data = EncryptionUtil.encrypt(paramObject.toString())

            Log.d("encryptedPaymentRequest", encryptedPaymentRequest.toString())

            RetrofitClientPayment.setBearerToken(sessionToken)
            val repository = PaymentRepository(RetrofitClientPayment.getPaymentInfo())
            val call = repository.getPaymentInfo(encryptedPaymentRequest)

            Log.d("BenGeldim2", loginResponse?.token.toString())
            call.enqueue(object : Callback<PaymentResponse> {
                //onResponse fonksiyonu, sunucudan dönen cevabı işlemek için kullanılır.
                override fun onResponse(call: Call<PaymentResponse>, response: Response<PaymentResponse>) {
                    Log.d("MainActivity", response.body().toString())
                    if (response.isSuccessful) {

                        Log.d("MainActivity", response.body().toString())
                        Log.d("MainActivity", "istek geldi ve başarılı !!!")
                        if (response.body()!= null){
                            Log.d("MainActivity", "isSuccessful")
                            val transactionResponse = response.body()!!
                            Log.d("transactionResponse", transactionResponse.toString())

                            binding.AmountText.text = transactionResponse.payment?.amount.toString()
                            binding.merchantNameText.text = transactionResponse.payment?.merchantName.toString()

                            var date = transactionResponse.payment?.date.toString().split(" ")
                            binding.LocalDateText.text = date[0]
                            binding.LocalTimeText.text = date[1]

                            binding.payButton.setOnClickListener{

                                getCardByUserEmail(paymentToken, loginResponse!!.token.toString(), email!!)

                            }
                            binding.cancelButton.setOnClickListener{
                                val deepLinkUrl = "http://www.saugetir5454.com/result?result=" + false;
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deepLinkUrl))
                                startActivity(intent)
                                activity?.finish()
                            }

                        }

                    }
                    else {
                        Log.d("MainActivity","else Hatası")
                        Log.d("MainActivity", response.errorBody().toString())

                        Toast.makeText(activity, "Service Error", Toast.LENGTH_SHORT).show()
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
                override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    Log.e("MainActivityError", t.message!!)
                }
            })


        } catch (e: JSONException) {
            e.printStackTrace()
        }
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
            val call = repository.getUserCardsForPayment(encryptedPaymentRequest)

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

                            if(cardResponse.data!!.cards!!.size == 0){
                                val action = PaymentFragmentDirections.actionPaymentFragmentToAddCardFragment(email)
                                findNavController().navigate(action)
                            }
                            else{
                                val action = PaymentFragmentDirections.actionPaymentFragmentToChooseCardFragment(cardResponse.data!!)
                                findNavController().navigate(action)
                            }
                        }

                    }
                    else {
                        Log.d("MainActivity","else Hatası")
                        Log.d("MainActivity", response.errorBody().toString())

                        /*viewAdapter = CardRecyclerAdapter(listOf())
                        viewPager = binding.viewPager2.apply {
                            adapter = viewAdapter
                        }*/
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