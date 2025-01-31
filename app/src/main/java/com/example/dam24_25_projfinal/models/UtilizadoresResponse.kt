package com.example.dam24_25_projfinal.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Utilizador(
    val user: String,
    val email: String,
    val password: String,
    val paginas: String,  // Agora é uma lista de inteiros
    val id: Int
)

@Serializable
data class UtilizadoresResponse(
    val utilizadores: Array<Utilizador>
)
