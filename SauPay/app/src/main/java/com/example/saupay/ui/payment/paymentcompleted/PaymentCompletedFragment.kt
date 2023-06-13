package com.example.saupay.ui.payment.paymentcompleted

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.saupay.R
import com.example.saupay.databinding.FragmentPaymentCompletedBinding
import com.example.saupay.databinding.FragmentTreeDSecureBinding
import com.example.saupay.ui.payment.treedsecure.TreeDSecureFragmentArgs


class PaymentCompletedFragment : Fragment() {


    private var _binding: FragmentPaymentCompletedBinding? = null
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentPaymentCompletedBinding.inflate(inflater, container, false)
        val bundle: PaymentCompletedFragmentArgs by navArgs()
        val merchantName = bundle.merchantName
        val amount = bundle.amount


        Log.d("FragmentPaymentComplete", "fragmentPaymentCompletedGeldim")

        binding.merchantNametText.text = merchantName
        binding.amountText.text = amount

        binding.button.setOnClickListener {
            val deepLinkUrl = "http://www.saugetir5454.com/result?result=" + true;
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deepLinkUrl))
            startActivity(intent)
            activity?.finish()
        }

        return binding.root
    }

}