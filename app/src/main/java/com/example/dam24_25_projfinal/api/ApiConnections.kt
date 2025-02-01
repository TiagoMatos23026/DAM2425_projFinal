package com.example.dam24_25_projfinal.api

import com.example.dam24_25_projfinal.models.Pagina
import com.example.dam24_25_projfinal.models.Paginae
import com.example.dam24_25_projfinal.models.PaginasResponse
import com.example.dam24_25_projfinal.models.Utilizador
import com.example.dam24_25_projfinal.models.Utilizadore
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

interface ApiConnections {

    /**
     * Pegar Páginas na Base de Dados
     */
    @GET("paginas")
    fun getAllPages(
        @Header("Authorization") authHeader: String?
    ): Call<PaginasResponse>

    @GET("utilizadores")
    fun getAllUsers4Login(
        @Header("Authorization") authHeader: String? = "Bearer segredo" // Token fixo
    ): Call<UtilizadoresResponse>

    @GET("utilizadores/{id}")
    fun getUserById(
        @Header("Authorization") authHeader: String?,
        @retrofit2.http.Path("id") userId: Int
    ): Call<Utilizadore>

    @POST("utilizadores")
    fun registerUser(
        @Header("Authorization") authHeader: String? = "Bearer segredo",
        @retrofit2.http.Body newUser: Utilizadore
    ): Call<Utilizadore>

    @POST("paginas")
    fun createPage(
        @Header("Authorization") authHeader: String?,
        @retrofit2.http.Body newPagina: Paginae
    ): Call<Paginae>




}