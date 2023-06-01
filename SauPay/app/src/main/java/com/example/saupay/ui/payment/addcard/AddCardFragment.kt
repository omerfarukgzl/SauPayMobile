package com.example.saupay.ui.payment.addcard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.saugetir.data.remote.model.request.EncryptedRequest
import com.example.saupay.data.remote.card.CardRepository
import com.example.saupay.data.remote.card.RetrofitClientCard
import com.example.saupay.data.remote.card.response.AddCardResponse
import com.example.saupay.data.remote.card.response.CardResponse
import com.example.saupay.databinding.FragmentAddCardBinding
import com.example.saupay.ui.payment.choosecard.CardRecyclerAdapter
import com.example.saupay.ui.payment.verification.PaymentActivty
import com.example.saupay.utils.EncryptionUtil
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddCardFragment : Fragment() {
    private var _binding: FragmentAddCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentAddCardBinding.inflate(inflater, container, false)

        val bundle: AddCardFragmentArgs by navArgs()
        val email = bundle.email

        val loginResponse= (activity as PaymentActivty).getLoginResponse()!!
        val paymentToken = (activity as PaymentActivty).getPaymentToken()!!

        binding.button2.setOnClickListener {

            val cardNumber = binding.cardNumberEditText.text.toString()
            val cardName = binding.cardHolderNameEditText.text.toString()
            val cardCvv = binding.cardCvvEditText.text.toString()
            val cardExpireDate = binding.cardDateEditText.text.toString()

            addCard(paymentToken, loginResponse.token.toString(), email, cardNumber, cardName, cardCvv, cardExpireDate)
        }



        return binding.root
    }



    fun addCard(paymentToken:String, sessionToken:String,email:String,cardNumber:String, cardName:String, cardCvv:String, cardExpireDate:String)
    {
        try {
            var encryptedPaymentRequest = EncryptedRequest()
            val paramObject = JSONObject()
            paramObject.put("email", email)
            paramObject.put("cardNumber", cardNumber)
            paramObject.put("binNumber", Integer.parseInt(cardNumber.substring(0, 6)))
            paramObject.put("cardHolderName", cardName)
            paramObject.put("cardCvv", cardCvv)
            paramObject.put("cardExpireDate", cardExpireDate)
            Log.d("paramObject", paramObject.toString() )

            encryptedPaymentRequest.data = EncryptionUtil.encrypt(paramObject.toString())

            Log.d("encryptedPaymentRequest", encryptedPaymentRequest.toString())

            RetrofitClientCard.setBearerToken(sessionToken)
            val repository = CardRepository(RetrofitClientCard.getUserCards())
            val call = repository.addCard(encryptedPaymentRequest)

            Log.d("BenGeldimCard", sessionToken)
            call.enqueue(object : Callback<AddCardResponse> {
                //onResponse fonksiyonu, sunucudan dönen cevabı işlemek için kullanılır.
                override fun onResponse(call: Call<AddCardResponse>, response: Response<AddCardResponse>) {
                    Log.d("MainActivity", response.body().toString())
                    if (response.isSuccessful) {

                        Log.d("MainActivity", response.body().toString())
                        Log.d("MainActivity", "istek geldi ve başarılı !!!")
                        if (response.body()!= null){
                            Log.d("MainActivity", "isSuccessful")
                            val cardResponse = response.body()!!
                            Log.d("transactionResponse", cardResponse.toString())

                            val action = AddCardFragmentDirections.actionAddCardFragmentToPaymentFragment()
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
                override fun onFailure(call: Call<AddCardResponse>, t: Throwable) {
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    Log.e("MainActivityError", t.message!!)
                }
            })


        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


}