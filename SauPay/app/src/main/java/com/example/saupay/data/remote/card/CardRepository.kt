package com.example.saupay.data.remote.card

import com.example.saugetir.data.remote.model.request.EncryptedRequest
import com.example.saupay.data.remote.card.response.CardResponse
import retrofit2.Call

class CardRepository(private var cardApi: CardApi)  {

     fun getUserCards(cardRequest: EncryptedRequest): Call<CardResponse> {
        return cardApi.requestCardsBankByUserEmailForPayment(cardRequest)
     }

}