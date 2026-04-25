package com.example.buscaminas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JuegoViewModel : ViewModel() {

    var tablero by mutableStateOf<List<List<CasillaEstado>>>(emptyList())
        private set

    var configActual: CfgPartida? = null
        private set

    companion object {
        const val TIEMPO_INICIAL = 25
    }

    var tiempoRestante by mutableIntStateOf(TIEMPO_INICIAL)
        private set

    var estadoPartida by mutableStateOf<TipoFin?>(null)
        private set

    private var contadorTiempo: Job? = null

    var totalMinas by mutableIntStateOf(0)
        private set

    var filaMina by mutableIntStateOf(-1)
        private set

    var columnaMina by mutableIntStateOf(-1)
        private set

    var configPartida: CfgPartida? by mutableStateOf(null)

    fun iniciarPartida(config: CfgPartida) {

        detenerTiempo()

        configActual = config
        tiempoRestante = TIEMPO_INICIAL
        estadoPartida = null
        filaMina = -1
        columnaMina = -1

        val totalCasillas = config.filas * config.columnas
        totalMinas = (totalCasillas * config.porcentajeMinas) / 100

        val tablero2 = List(config.filas) {
            MutableList(config.columnas) {
                CasillaEstado()
            }
        }

        repeat(totalMinas) {
            var fila: Int
            var columna: Int

            do {
                fila = (0 until config.filas).random()
                columna = (0 until config.columnas).random()
            } while (tablero2[fila][columna].esMina)

            tablero2[fila][columna].esMina = true
        }

        for (fila in 0 until config.filas) {
            for (columna in 0 until config.columnas) {

                if (!tablero2[fila][columna].esMina) {

                    var contador = 0

                    for (movFila in -1..1) {
                        for (movColumna in -1..1) {

                            if (movFila == 0 && movColumna == 0) continue

                            val nuevaFila = fila + movFila
                            val nuevaColumna = columna + movColumna

                            if (
                                nuevaFila in 0 until config.filas &&
                                nuevaColumna in 0 until config.columnas &&
                                tablero2[nuevaFila][nuevaColumna].esMina
                            ) {
                                contador++
                            }
                        }
                    }

                    tablero2[fila][columna].minasAlrededor = contador
                }
            }
        }

        tablero = tablero2
    }

    fun descubrirCasilla(fila: Int, columna: Int) {
        val casilla = tablero[fila][columna]

        if (casilla.descubierta) return

        casilla.descubierta = true

        if (casilla.esMina) {
            filaMina = fila
            columnaMina = columna

            detenerTiempo()
            estadoPartida = TipoFin.MINA
            return
        }

        comprobarVictoria()
    }
    private fun comprobarVictoria() {
        val casillasSinMinas = tablero.flatten().count { !it.esMina }
        val descubiertas = tablero.flatten().count { it.descubierta && !it.esMina }

        if (descubiertas == casillasSinMinas) {
            detenerTiempo()
            estadoPartida = TipoFin.VICTORIA
        }
    }
    fun iniciarTiempo() {

        if (contadorTiempo?.isActive == true) return
        if (estadoPartida != null) return //Si ha terminado que pare

        contadorTiempo = viewModelScope.launch {

            tiempoRestante = TIEMPO_INICIAL

            while (tiempoRestante > 0) {
                delay(1000)
                tiempoRestante--
            }

            estadoPartida = TipoFin.TIEMPO
        }
    }

    fun detenerTiempo() {
        contadorTiempo?.cancel()
    }

    fun consumirEstadoPartida() {
        estadoPartida = null
    }

    fun resetPartida() {
        tablero = emptyList()
        estadoPartida = null
        filaMina = -1
        columnaMina = -1
        detenerTiempo()
    }


}