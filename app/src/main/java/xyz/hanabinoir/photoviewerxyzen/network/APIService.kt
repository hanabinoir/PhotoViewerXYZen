package xyz.hanabinoir.photoviewerxyzen.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIService {

    private const val BASE_URL = "https://api.pexels.com/v1/"

    private val interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "qlQQihDlKANM52FbrG9XGQ61LCDz96rXYpXFhHCvFemNWfqaevuhdXj9")
            .build()
        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val photoService: PhotoService = retrofit.create(PhotoService::class.java)
}