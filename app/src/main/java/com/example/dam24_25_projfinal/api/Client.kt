package com.example.dam24_25_projfinal.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {

    //URL da API
    private const val BASE_URL = "https://api.sheety.co/603075854cd9316246fab517d2525742/damProjFinal/"

    // Interceptor para logging das chamadas à API
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level =
            HttpLoggingInterceptor.Level.BODY // Configura o nível de logging para capturar o corpo das requisições
    }

    //Cliente HTTP
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor) // Adiciona o interceptor para logging
        .build()

    // Instância do serviço API criada de forma lazy (somente quando for utilizada pela primeira vez)
    val instance: ApiConnections by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Define a URL base da API
            .client(okHttpClient) // Usa o cliente HTTP configurado
            .addConverterFactory(GsonConverterFactory.create()) // Adiciona suporte à conversão de JSON usando Gson
            .build() // Constrói a instância do Retrofit
            .create(ApiConnections::class.java) // Cria a implementação da interface ApiService
    }
}