package com.example.saupay.ui.payment.treedsecure

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.saupay.R
import com.example.saupay.databinding.FragmentTreeDSecureBinding
import com.example.saupay.ui.payment.choosecard.ChooseCardFragmentArgs
import com.example.saupay.ui.payment.verification.PaymentActivty

class TreeDSecureFragment : Fragment() {

    private var _binding: FragmentTreeDSecureBinding? = null
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTreeDSecureBinding.inflate(inflater, container, false)

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

       return binding.root
    }




}