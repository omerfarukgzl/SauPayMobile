package com.example.saupay.ui.payment.treedsecure

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.saugetir.data.remote.model.request.EncryptedRequest
import com.example.saupay.R
import com.example.saupay.data.remote.card.PaymentBankRepository
import com.example.saupay.data.remote.card.RetrofitClientPaymentBank
import com.example.saupay.data.remote.payment.PaymentRepository
import com.example.saupay.data.remote.payment.RetrofitClientPayment
import com.example.saupay.data.remote.payment.response.PaymentResponse
import com.example.saupay.data.remote.treedsecure.response.PaymentBankResponse
import com.example.saupay.databinding.FragmentTreeDSecureBinding
import com.example.saupay.ui.payment.choosecard.ChooseCardFragmentArgs
import com.example.saupay.ui.payment.verification.PaymentActivty
import com.example.saupay.utils.EncryptionUtil
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal

class TreeDSecureFragment : Fragment() {

    private var _binding: FragmentTreeDSecureBinding? = null
    private val binding get() = _binding!!

    var time:Long=180

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTreeDSecureBinding.inflate(inflater, container, false)

        Log.d("FragmentTreeDsecure", "fragmentTreeDSecureGeldim")


        val loginResponse= (activity as PaymentActivty).getLoginResponse()!!
        val paymentToken = (activity as PaymentActivty).getPaymentToken()!!

        val bundle: TreeDSecureFragmentArgs by navArgs()
        val merchantName = bundle.merchantName
        val amount = bundle.amount
        val cardNumber = bundle.cardNumber
        val date = bundle.date
        val clock  = bundle.clock
        val telNumber = bundle.telNumber

        Log.d("TreeDSecureFragment", "merchantName: $merchantName")
        Log.d("TreeDSecureFragment", "amount: $amount")
        Log.d("TreeDSecureFragment", "cardNumber: $cardNumber")
        Log.d("TreeDSecureFragment", "date: $date")
        Log.d("TreeDSecureFragment", "clock: $clock")
        Log.d("TreeDSecureFragment", "telNumber: $telNumber")

        binding.merchantName.text = merchantName
        binding.amount.text = amount
        binding.cardNumber.text = cardNumber
        binding.date.text = date
        binding.clock.text = clock
        binding.phoneNumber.text = telNumber

        object : CountDownTimer(time*1000, 1000) {//time değişkenimizi long türünde tanımlayıp timerın ne kadar süreceğini belirtiyoruz fakat 1000 ile çarpıyoruz ki saniye olarak alabilsin.
        override fun onFinish() {//Süre bittiğinde fonksiyonunda time değişkenini sıfırla dedik
            time=0
        }
            override fun onTick(p0: Long) {//Her saniye başı geldiğinde yapılacak işlemler
                time--
                binding.timer.text= time.toString()
            }
        }.start()

        binding.okButton.setOnClickListener {
            if (binding.VerifyCode.text.toString().equals("545454"))
            {
                //val action = TreeDSecureFragmentDirections.actionTreeDSecureFragmentToPaymentCompletedFragment(merchantName,amount)
                payment(paymentToken, loginResponse.token!!, amount.toBigDecimal(), "205", cardNumber,merchantName)
                //findNavController().navigate(action)
            }
            else
            {
                Log.d("TreeDSecureFragment", "VerifyCode: " + binding.VerifyCode.text.toString())
                Toast.makeText(activity, "Doğrulama kodu hatalı", Toast.LENGTH_SHORT).show()
            }
        }

        binding.cancelButton2.setOnClickListener{
            val deepLinkUrl = "http://www.saugetir5454.com/result?result=" + false;
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deepLinkUrl))
            startActivity(intent)
            activity?.finish()
        }

       return binding.root
    }





    fun payment(paymentToken:String, sessionToken:String,amount:BigDecimal, bankCode:String, cardNumber:String, merchantName:String)
    {
        try {
            var encryptedPaymentRequest = EncryptedRequest()
            val paramObject = JSONObject()
            paramObject.put("amount", amount)
            paramObject.put("bankCode", bankCode)
            paramObject.put("cardNumber", cardNumber)
            paramObject.put("token", paymentToken)

            Log.d("paramObject", paramObject.toString() )

            encryptedPaymentRequest.data = EncryptionUtil.encrypt(paramObject.toString())

            Log.d("encryptedPaymentRequest", encryptedPaymentRequest.toString())

            RetrofitClientPaymentBank.setBearerToken(sessionToken)
            val repository = PaymentBankRepository(RetrofitClientPaymentBank.paymentBank())
            val call = repository.paymentBank(encryptedPaymentRequest)

            call.enqueue(object : Callback<PaymentBankResponse> {
                //onResponse fonksiyonu, sunucudan dönen cevabı işlemek için kullanılır.
                override fun onResponse(call: Call<PaymentBankResponse>, response: Response<PaymentBankResponse>) {
                    Log.d("TreeDSecureFragmentNew", response.body().toString())
                    if (response.isSuccessful) {

                        Log.d("MainActivity", response.body().toString())
                        Log.d("MainActivity", "istek geldi ve başarılı !!!")
                        if (response.body()!= null){
                            Log.d("MainActivity", "isSuccessful")
                            val paymentBankResponse = response.body()!!
                            Log.d("transactionResponse", paymentBankResponse.toString())

                            val action = TreeDSecureFragmentDirections.actionTreeDSecureFragmentToPaymentCompletedFragment(merchantName,amount.toString())
                            findNavController().navigate(action)
                        }

                    }
                    else {

                        Toast.makeText(activity, "Ödeme Başarısız", Toast.LENGTH_LONG).show()
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
                override fun onFailure(call: Call<PaymentBankResponse>, t: Throwable) {
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    Log.e("MainActivityError", t.message!!)
                }
            })


        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }








}