package com.bendev.networkmanager.data.network

import com.bendev.networkmanager.utils.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkManager {

    // TODO Add network services

    private val httpClient = OkHttpClient.Builder()
        .writeTimeout(Constants.NETWORK_TIMEOUT_IN_S, TimeUnit.SECONDS)
        .readTimeout(Constants.NETWORK_TIMEOUT_IN_S, TimeUnit.SECONDS)
        // Add network interceptor
        //.addNetworkInterceptor()
        .build()

    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .setDateFormat(Constants.GSON_DATE_FORMAT)
        .create()

    private val retrofit = Retrofit.Builder()
        // TODO Set up base url
        //.baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient)
        .build()

}