package com.example.saupay.data.remote.card

import com.example.saugetir.data.remote.model.request.EncryptedRequest
import com.example.saupay.data.remote.treedsecure.response.TreeDSecureResponse
import retrofit2.Call

class TreeDSecureRepository(private var treeDSecureApi: TreeDSecureApi)  {

     fun getUserCards(cardRequest: EncryptedRequest): Call<TreeDSecureResponse> {
        return treeDSecureApi.paymentCompleteRequestForTreeDSecure(cardRequest)
     }

}