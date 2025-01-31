package com.example.dam24_25_projfinal.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val gson: Gson = GsonBuilder().setLenient().create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.sheety.co/603075854cd9316246fab517d2525742/damProjFinal/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun ApiConnections() = retrofit.create(ApiConnections::class.java)

}