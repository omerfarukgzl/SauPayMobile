package com.example.saupay.data.remote.transaction

import android.util.Log
import com.example.saupay.utils.Constants.BASE_URL
import com.example.saupay.utils.Constants.MERCHANT_CODE
import com.example.saupay.utils.EncryptionUtil.generateRandomKey
import com.example.saupay.utils.EncryptionUtil.generateSignature
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClientTransaction {

    private var bearerToken: String = ""

    private var transactionApi: TransactionApi? = null

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
        Log.d("requestUrl", requestUrl);

        requestBuilder
/*            .addHeader("x-signature", generateSignature(randomKey, requestBodyString))
            .addHeader("x-merchant-code", MERCHANT_CODE)
            .addHeader("x-rnd-key", randomKey)*/
            .addHeader("Authorization", "Bearer " + bearerToken)
        chain.proceed(requestBuilder.build())
    }

    fun getTransaction(): TransactionApi {
        if (transactionApi == null) {
            //interceptor.level = HttpLoggingInterceptor.Level.BODY
            Log.d("BearerTokenSetlendi", bearerToken)
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(customHeaderInterceptor)
                .build()

            transactionApi = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(TransactionApi::class.java)

        }
        return transactionApi!!
    }

    fun setBearerToken(token: String) {
        bearerToken = token
    }
    fun getBearerToken(): String {
        return bearerToken
    }


/*

        İlk adımda, bearerToken adında bir değişken tanımlanır ve Bearer token değeri <YOUR_BEARER_TOKEN> ile atanır.
        Bu değeri, kendi Bearer token değerinize uygun şekilde değiştirmeniz gerekmektedir.

        customHeaderInterceptor adında bir Interceptor tanımlanır. Bu interceptor, her isteğin başlıklarına ek bilgileri eklemek için kullanılır.
        Interceptor arayüzünü uygulayan bir lambda ifadesi kullanılır. Bu ifade, chain nesnesi üzerinden orijinal isteği alır,
        başlıkları günceller ve güncellenmiş isteği devam ettirir.

        İnterceptor içinde, requestBuilder adında bir Request.Builder nesnesi oluşturulur. Ardından, requestBuilder üzerinden isteğin URL'sini, body'sini ve diğer bilgilerini elde ederiz.

        randomKey adında rastgele bir anahtar (key) oluşturulur. Bu anahtar, isteğe eklenen diğer başlıklarla birlikte imza oluşturma işleminde kullanılacaktır.

        requestBuilder üzerinden addHeader metodu çağrılarak isteğin başlıklarına x-signature, x-merchant-code ve x-rnd-key başlıkları eklenir.
        Bu başlıklar, isteği imzalama veya kimlik doğrulama için kullanılan belirli bilgileri temsil eder.

        Son olarak, Bearer token'ı eklemek için addHeader("Authorization", "Bearer $bearerToken") ifadesi kullanılır. Bu ifade, Authorization başlığına Bearer token'ını ekler.

        chain.proceed(requestBuilder.build()) ifadesiyle güncellenmiş istek devam ettirilir.

        Son olarak, OkHttpClient oluşturulurken, customHeaderInterceptor interceptor'ı addInterceptor metoduyla eklenir. Bu sayede, her istek için belirtilen başlıklar eklenir.

        Bu şekilde, customHeaderInterceptor aracılığıyla her isteğin başlıklarına Bearer token'ınızı eklemiş olursunuz.



    */







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