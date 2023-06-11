package com.example.saupay.data.remote.card

import com.example.saupay.utils.Constants.BASE_URL
import com.example.saupay.utils.Constants.MERCHANT_CODE
import com.example.saupay.utils.EncryptionUtil.generateRandomKey
import com.example.saupay.utils.EncryptionUtil.generateSignature
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClientPaymentBank {

    private var bearerToken: String = ""

    private var paymentBankApi: PaymentBankApi? = null

    private val interceptor = HttpLoggingInterceptor() //Kodunuzda oluşturulan interceptor örneği, HTTP istekleri ve yanıtları hakkında bilgi almak için kullanılabilir. Bu örneği, OkHttpClient.Builder'a ekleyerek günlüğe kaydedilecek log seviyesini belirleyebilirsiniz. Örneğin, aşağıdaki gibi interceptor'u ekleyebilirsiniz:

    private val customHeaderInterceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        val request = chain.request()
        val requestBody = request.body
        val buffer = Buffer()
        requestBody?.writeTo(buffer)
        val requestBodyString = buffer.readString(Charsets.UTF_8)
        val randomKey = generateRandomKey()

        requestBuilder
            .addHeader("x-signature", generateSignature(randomKey, requestBodyString))
            .addHeader("x-merchant-code", MERCHANT_CODE)
            .addHeader("x-rnd-key", randomKey)
            .addHeader("Authorization", "Bearer " + bearerToken)

        chain.proceed(requestBuilder.build())
    }

    fun paymentBank(): PaymentBankApi {
        if (paymentBankApi == null) {
            //interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(customHeaderInterceptor)
                .build()

            val gson = GsonBuilder().disableHtmlEscaping().create()
            val gsonConverterFactory = GsonConverterFactory.create(gson)

            paymentBankApi = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .client(client)
                .build()
                .create(PaymentBankApi::class.java)

        }
        return paymentBankApi!!
    }

    fun setBearerToken(token: String) {
        bearerToken = token
    }

    fun getBearerToken(): String {
        return bearerToken
    }











/*    fun getClient(baseUrl: String): Retrofit {

            val httpClient = OkHttpClient.Builder()

            // Add Authorization header to every request
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Authorization", "your_token_here")
                    .build()
                chain.proceed(request)
            }

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }*/
}




/* private var merchantApi: MerchantApi? = null
    private val interceptor = HttpLoggingInterceptor()

    private val customHeaderInterceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        val request = chain.request()
        val requestUrl = request.url.toString()
        val requestBody = request.body
        val buffer = Buffer()
        requestBody?.writeTo(buffer)
        val requestBodyString = buffer.readString(Charsets.UTF_8)
        val randomKey = generateRandomKey()

        requestBuilder
            .addHeader("x-signature", generateSignature(requestUrl, randomKey, requestBodyString))
            .addHeader("x-auth-version", Constant.VERSION)
            .addHeader("x-api-key", Constant.API_KEY)
            .addHeader("x-rnd-key", randomKey)

        chain.proceed(requestBuilder.build())
    }

    fun getOderoApi(): MerchantApi {
        if (merchantApi == null) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(customHeaderInterceptor)
                .build()

            merchantApi = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(MerchantApi::class.java)

        }
        return merchantApi!!
    }

*/