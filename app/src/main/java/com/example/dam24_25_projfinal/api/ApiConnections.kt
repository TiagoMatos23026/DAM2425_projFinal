package com.example.dam24_25_projfinal.api

import com.example.dam24_25_projfinal.models.PaginaResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Endpoints da API
 */

interface ApiConnections{

    /**
     * Pegar Páginas na Base de Dados
     */
    @GET("paginas") // Endpoint para obter a lista de utilizadores
    fun getAllPages(): PaginaResponse
}