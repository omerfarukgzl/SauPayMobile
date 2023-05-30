package com.example.saupay.ui.payment.paymentcompleted

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.saupay.R
import com.example.saupay.databinding.FragmentTreeDSecureBinding


class PaymentCompletedFragment : Fragment() {


    private var _binding: FragmentTreeDSecureBinding? = null
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentTreeDSecureBinding.inflate(inflater, container, false)
        binding.okButton.setOnClickListener {
            activity?.finish()
        }

        return binding.root
    }

}