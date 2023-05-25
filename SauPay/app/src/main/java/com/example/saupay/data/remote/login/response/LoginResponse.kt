package com.example.saupay.data.remote.login.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponse(
    @SerializedName("access_token")
    var token: String?,
    @SerializedName("expires_in")
    var expiresIn : Long?,
    @SerializedName("refresh_expires_in")
    var refreshExpiresIn : Long?,
    @SerializedName("refresh_token")
    var refreshToken : String?,
    @SerializedName("token_type")
    var tokenType : String?,
    @SerializedName("id_token")
    var idToken : String?,
    @SerializedName("not-before-policy")
    var notBeforePolicy : Int?,
    @SerializedName("session_state")
    var sessionState : String?,
    @SerializedName("otherClaims")
    var  otherClaims :Map<String?, Object>,
    @SerializedName("scope")
    var scope : String?,
    @SerializedName("error")
    var error : String?,
    @SerializedName("error_description")
    var errorDescription : String?,
    @SerializedName("error_uri")
    var errorUri : String?
) : Serializable {
}