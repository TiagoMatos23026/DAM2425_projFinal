package com.example.dam24_25_projfinal.api

import com.example.dam24_25_projfinal.models.PaginasResponse
import com.example.dam24_25_projfinal.models.UtilizadoresResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Endpoints da API
 */

interface ApiConnections{

    /**
     * Pegar Páginas na Base de Dados
     */
    @GET("paginas")
    fun getAllPages(): Call<PaginasResponse>

    @GET("utilizadores")
    fun getAllUsers(): Call<UtilizadoresResponse>
}