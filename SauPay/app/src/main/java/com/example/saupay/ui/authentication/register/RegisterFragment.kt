package com.example.saupay.ui.authentication.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.saupay.data.remote.login.LoginRepository
import com.example.saupay.data.remote.login.RetrofitClientLogin
import com.example.saupay.data.remote.login.request.RegisterRequest
import com.example.saupay.data.remote.login.response.RegisterResponse
import com.example.saupay.databinding.FragmentRegisterBinding
import com.example.saupay.ui.authentication.login.LoginFragmentDirections
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentRegisterBinding.inflate(inflater, container, false)




        binding.registerButton3.setOnClickListener{
            val email = binding.editTextTextPersonEmail.text.toString()
            val password = binding.editTextTextPersonPassword.text.toString()
            val username = binding.editTextTextPersonName.text.toString()
            val surname = binding.editTextTextPersonSurname.text.toString()
            val phone = binding.editTextTextPersonTelephone.text.toString()
            val tc = binding.editTextTextPersonTc.text.toString()
            register(email.toString(),password.toString(),username.toString(),surname.toString(),phone.toString(),tc.toString())
        }



        return binding.root
    }


    fun register(email:String,password :String,username:String,surname:String,phone:String,tc:String)
    {

        try {



            val registerRequest = RegisterRequest(email,password,username,surname,phone,tc)

            val repository = LoginRepository(RetrofitClientLogin.getTransaction())
            val call = repository.registerRequest(registerRequest)

            call.enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {

                        if (response.body()!= null){
                            Log.d("MainActivity", "isSuccessful")
                            val loginResponse = response.body()

                            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                            findNavController().navigate(action)

                        }
                        else{
                            Log.d("MainActivity", "isSuccessful but body is null")
                        }
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
/*                    val context = requireContext()
                    if (context != null) {
                        val packageName = context.packageName*/
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    Log.d("Omer", "onFailure: " + t.message)
                    //  }

                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


}