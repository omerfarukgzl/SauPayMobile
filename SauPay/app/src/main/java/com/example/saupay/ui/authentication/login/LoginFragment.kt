package com.example.saupay.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.saupay.data.remote.login.LoginRepository
import com.example.saupay.data.remote.login.RetrofitClientLogin
import com.example.saupay.data.remote.login.request.LoginRequest
import com.example.saupay.data.remote.login.response.LoginResponse
import com.example.saupay.databinding.FragmentLoginBinding
import com.example.saupay.ui.authentication.AuthenticationActivty
import com.example.saupay.ui.MainActivity
import com.example.saupay.ui.payment.PaymentActivty
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var paymentToken : String?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        paymentToken = (activity as AuthenticationActivty).getPaymentToken()!!

        binding.loginButton.setOnClickListener{

            sendLoginRequest(binding.username.text.toString(),binding.password.text.toString())

        }

        return binding.root

    }

    fun sendLoginRequest(email:String,password :String)
    {
        try {
            var loginRequest = LoginRequest(email,password)
            Log.d("Omer", "Login Request: -->" +loginRequest.toString())


            //encryptedTokenRequest.data = EncryptionUtil.encrypt(paramObject.toString())

            val repository = LoginRepository(RetrofitClientLogin.getTransaction())
            val call = repository.loginRequest(loginRequest)

            call.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {

                        if (response.body()!= null){
                            Log.d("MainActivity", "isSuccessful")
                            val loginResponse = response.body()
                            if(paymentToken != ""){
                                val intent = Intent(activity, PaymentActivty::class.java)
                                intent.putExtra("Login_Response",loginResponse)
                                intent.putExtra("Payment_Token",paymentToken)
                                intent.putExtra("Email",email)
                                startActivity(intent)
                            }
                            else{
                                val intent = Intent(activity, MainActivity::class.java)
                                intent.putExtra("Login_Response",loginResponse)
                                startActivity(intent)
                            }
                            onDestroyView()
                        }
                        else{
                            Log.d("MainActivity", "isSuccessful but body is null")
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    Log.d("Omer", "onFailure: " + t.message)
                }




            })
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

}