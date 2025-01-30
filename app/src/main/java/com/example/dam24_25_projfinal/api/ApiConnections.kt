package com.example.dam24_25_projfinal.api

import com.example.dam24_25_projfinal.models.Paginas
import retrofit2.http.GET

/**
 * Endpoints da API
 */

interface ApiConnections{

    /**
     * Pegar Páginas na Base de Dados
     */
    @GET("paginas") // Endpoint para obter a lista de utilizadores
    fun getAllPages(): Paginas
}