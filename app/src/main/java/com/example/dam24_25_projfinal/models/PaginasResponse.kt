package com.example.dam24_25_projfinal.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pagina(
    val titulo: String,
    val texto: String?,
    val utilizador: Int,
    val id: Int?
)

data class Paginae(
    val pagina: Pagina
)


@Serializable
data class PaginasResponse(
    val paginas: Array<Pagina>
)