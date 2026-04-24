package com.example.buscaminas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    var tiempoRestante by mutableStateOf(TIEMPO_INICIAL)
        private set

    fun iniciarPartida(config: CfgPartida) {
        configActual = config

        val tablero2 = List(config.filas) {
            MutableList(config.columnas) {
                CasillaEstado()
            }
        }

        val totalCasillas = config.filas * config.columnas
        val totalMinas = (totalCasillas * config.porcentajeMinas) / 100

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

    fun iniciarTiempo(onFin: () -> Unit) {
        viewModelScope.launch {
            tiempoRestante = TIEMPO_INICIAL

            while (tiempoRestante > 0) {
                delay(1000)
                tiempoRestante--
            }

            onFin()
        }
    }
}