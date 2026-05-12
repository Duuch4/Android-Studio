package com.example.buscaminas.data.data

class PartidaRepository(
    private val partidaDao: PartidaDao
) {

    suspend fun insertarPartida(partida: PartidaEntity) {
        partidaDao.insertarPartida(partida)
    }

    suspend fun obtenerPartidas(): List<PartidaEntity> {
        return partidaDao.obtenerPartidas()
    }

    suspend fun borrarPartidas() {
        partidaDao.borrarPartidas()
    }
}