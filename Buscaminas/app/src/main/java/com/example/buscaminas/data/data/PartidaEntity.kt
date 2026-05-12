package com.example.buscaminas.data.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "partidas")
data class PartidaEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val alias: String,

    val fecha: String,

    val filas: Int,

    val columnas: Int,

    val porcentajeMinas: Int,

    val totalMinas: Int,

    val tiempoRestante: Int,

    val casillasRestantes: Int,

    val filaMina: Int,

    val columnaMina: Int,

    val resultado: String
)