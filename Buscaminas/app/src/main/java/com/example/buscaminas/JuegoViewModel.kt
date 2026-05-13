package com.example.buscaminas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.buscaminas.data.data.AppDatabase
import com.example.buscaminas.data.data.PartidaEntity
import com.example.buscaminas.data.data.PartidaRepository

class JuegoViewModel(application: Application) : AndroidViewModel(application){

    private val partidaDao = AppDatabase.getDatabase(application).partidaDao()

    private val repository = PartidaRepository(partidaDao)

    private var ultimaHora = ""

    var historialPartidas by mutableStateOf<List<PartidaEntity>>(emptyList())
        private set

    fun cargarPartidas() {

        viewModelScope.launch {

            historialPartidas = repository.obtenerPartidas()
        }
    }

    var logPartida by mutableStateOf<List<String>>(emptyList())
        private set

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

        val context = getApplication<Application>()

        val totalCasillas = config.filas * config.columnas
        totalMinas = (totalCasillas * config.porcentajeMinas) / 100

        val tablero2 = List(config.filas) {
            MutableList(config.columnas) {
                CasillaEstado()
            }
        }

        logPartida = listOf(
            context.getString(R.string.log_alias, config.alias),
            context.getString(R.string.log_tablero, config.filas, config.columnas),
            context.getString(R.string.log_Porcentaje_minas, config.porcentajeMinas),
            context.getString(R.string.log_minas, totalMinas),
            if (config.tiempoActivo)
                context.getString(R.string.log_control_tiempo)
            else
                context.getString(R.string.log_sin_control_tiempo)
        )

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
        val context = getApplication<Application>()
        val casilla = tablero[fila][columna]

        if (casilla.descubierta) return

        val formatter = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault())

        val horaActual = formatter.format(java.util.Date())

        casilla.descubierta = true

        var texto = context.getString(R.string.log_casilla_seleccionada, fila, columna)

        if (ultimaHora.isNotEmpty()) {
            texto += "\n" + ultimaHora
        }
        texto += "\n" + horaActual

        if (configActual?.tiempoActivo == true) {
            texto += "\n" + context.getString(R.string.log_tiempo_restante, tiempoRestante)
        }

        logPartida = logPartida + texto
        ultimaHora = horaActual

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

    fun guardarPartida(config: CfgPartida, resultado: String) {

        viewModelScope.launch {

            val partida = PartidaEntity(

                alias = config.alias,

                fecha = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date()),

                filas = config.filas,

                columnas = config.columnas,

                porcentajeMinas = config.porcentajeMinas,

                totalMinas = totalMinas,

                tiempoRestante = tiempoRestante,

                casillasRestantes =
                    tablero.flatten().count { !it.descubierta },

                filaMina = filaMina,

                columnaMina = columnaMina,

                resultado = resultado
            )

            repository.insertarPartida(partida)
        }
    }


}