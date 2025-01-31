package com.example.dam24_25_projfinal.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Utilizador(
    val user: String,
    val email: String,
    val password: String,
    val paginas: String,
    val biografia: String,
    val id: Int
)

data class Utilizadore(
    val utilizadore: Utilizador
)

@Serializable
data class UtilizadoresResponse(
    val utilizadores: Array<Utilizador>
)
