package com.example.dam24_25_projfinal.api

import com.example.dam24_25_projfinal.models.Pagina
import com.example.dam24_25_projfinal.models.Paginae
import com.example.dam24_25_projfinal.models.PaginasResponse
import com.example.dam24_25_projfinal.models.Utilizador
import com.example.dam24_25_projfinal.models.Utilizadore
import com.example.dam24_25_projfinal.models.UtilizadoresResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiConnections {

    /**
     * Metodo GET para buscar a lista de paginas
     */
    @GET("paginas")
    fun getAllPages(
        @Header("Authorization") authHeader: String?
    ): Call<PaginasResponse>

    /**
     * Metodo GET para buscar a lista de utilizadores
     */
    @GET("utilizadores")
    fun getAllUsers4Login(
        @Header("Authorization") authHeader: String? = "Bearer segredo" // Token fixo
    ): Call<UtilizadoresResponse>

    /**
     * Metodo GET para buscar utilizador por ID
     */
    @GET("utilizadores/{id}")
    fun getUserById(
        @Header("Authorization") authHeader: String?,
        @retrofit2.http.Path("id") userId: Int
    ): Call<Utilizadore>

    /**
     * Metodo POST para criar novo utilizador
     */
    @POST("utilizadores")
    fun registerUser(
        @Header("Authorization") authHeader: String? = "Bearer segredo",
        @retrofit2.http.Body newUser: Utilizadore
    ): Call<Utilizadore>

    /**
     * Metodo POST para criar nova pagina
     */
    @POST("paginas")
    fun createPage(
        @Header("Authorization") authHeader: String?,
        @retrofit2.http.Body newPagina: Paginae
    ): Call<Paginae>

    /**
     * Metodo DELETE para eliminar utilizadores
     */
    @DELETE("utilizadores/{id}")
    fun deleteUser(
        @Header("Authorization") authHeader: String?,
        @retrofit2.http.Path("id") userId: Int
    ): Call<Void>

    /**
     * Metodo DELETE para eliminar paginas
     */
    @DELETE("paginas/{id}")
    fun deletePagina(
        @Header("Authorization") authHeader: String?,
        @retrofit2.http.Path("id") paginaId: Int
    ):Call<Void>

    /**
     * Metodo put para atualizar os utilizadores
     */
    @PUT("utilizadores/{id}")
    fun editBio(
        @Header("Authorization") authHeader: String?,
        @retrofit2.http.Path("id") userId: Int,
        @retrofit2.http.Body newUserInfo: Utilizadore
    ): Call<Utilizadore>

}