package com.skilbox.mypokemons.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NetworkRetrofit {

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    private val retrofit = retrofit2.Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://pokeapi.co/api/v2/")
        .client(okHttpClient)
        .build()

    val api: PokeApi
        get() = retrofit.create()
}
