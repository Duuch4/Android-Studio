package com.example.buscaminas.data.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PartidaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPartida(partida: PartidaEntity)

    @Query("SELECT * FROM partidas ORDER BY id DESC")
    suspend fun obtenerPartidas(): List<PartidaEntity>

    @Query("DELETE FROM partidas")
    suspend fun borrarPartidas()
}