package com.example.dam24_25_projfinal.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val gson: Gson = GsonBuilder().setLenient().create()

    private val retrofit = Retrofit.Builder()
        //.baseUrl("http://10.0.2.2/")
        .baseUrl("http://ram.ipt.pt")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun noteService() = retrofit.create(ApiConnections::class.java)

}