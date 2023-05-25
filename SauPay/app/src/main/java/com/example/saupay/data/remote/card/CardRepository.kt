package com.example.saupay.data.remote.card

import com.example.saugetir.data.remote.model.request.EncryptedPaymentRequest
import com.example.saupay.data.remote.card.response.CardResponse
import com.example.saupay.data.remote.payment.response.PaymentResponse
import retrofit2.Call

class CardRepository(private var cardApi: CardApi)  {

     fun getUserCards(cardRequest: EncryptedPaymentRequest): Call<CardResponse> {
        return cardApi.requestCardsBankByUserEmail(cardRequest)
     }

}