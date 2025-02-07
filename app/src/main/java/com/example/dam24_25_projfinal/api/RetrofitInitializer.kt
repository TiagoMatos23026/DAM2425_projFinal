package com.example.dam24_25_projfinal.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val gson: Gson = GsonBuilder().setLenient().create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.sheety.co/0aa4db5794a943d438f39bd9d0787a89/damProj/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    /**
     * Funcao para iniciar o retrofit
     */
    fun ApiConnections() = retrofit.create(ApiConnections::class.java)

}