package com.example.unsplashphotogallery.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RemoteDataSource @Inject constructor(@ApplicationContext var context: Context) {


    fun <Api> buildApi(api: Class<Api>): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader(
                            "Authorization",
                            "Client-ID Rj5x5z9jejlr2SWxhfTlgxQilAyIVby7MT7TwcH0C2s"
                        )
                        .build()
                    chain.proceed(request)
                }.also {
                    it.addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
                .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }

    companion object {
        private const val BASE_URL = "https://api.unsplash.com/"
    }

}