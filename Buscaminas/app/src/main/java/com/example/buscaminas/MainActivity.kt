package com.example.buscaminas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buscaminas.ui.theme.BuscaminasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BuscaminasTheme {
                MyApp()
            }
        }
    }
}


@Composable
fun MyApp() {

    var pantallaActual by rememberSaveable { mutableStateOf("Principal") }
    var configPartida by rememberSaveable { mutableStateOf<CfgPartida?>(null) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        when(pantallaActual){

            "Principal" -> Principal(
                modifier = Modifier.padding(innerPadding),
                onIrAyuda = { pantallaActual = "Ayuda" },
                onEmpezarpartida = { pantallaActual = "Configuracion" }
            )

            "Ayuda" -> Ayuda(
                modifier = Modifier.padding(innerPadding),
                onVolver = { pantallaActual = "Principal" }
            )

            "Configuracion" -> Configuracion(
                modifier = Modifier.padding(innerPadding),
                onEmpezar = {
                    configPartida = it
                    pantallaActual = "Juego"
                }
            )

            "Juego" -> {
                configPartida?.let { config ->
                    Juego(
                        modifier = Modifier.padding(innerPadding),
                        config = config
                    )
                }
            }
        }
    }
}
@Composable
fun Principal(modifier: Modifier = Modifier,onIrAyuda: () -> Unit,onEmpezarpartida: () -> Unit) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        Header(
            titulo = stringResource(id = R.string.menu_principal),
            icono = R.drawable.icono_mina
        )

        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Button(onClick = onIrAyuda) {
                Text(text = stringResource(id = R.string.boton_ayuda))
            }

            ElevatedButton(onClick = onEmpezarpartida) {
                Text(text = stringResource(id = R.string.boton_empezar))
            }

            Button(onClick = { }) {
                Text(text = stringResource(id = R.string.boton_salir))
            }
        }
    }
}

@Composable
fun Ayuda(modifier: Modifier = Modifier, onVolver: () -> Unit) {

    Column(
        modifier = modifier.fillMaxSize()

    ) {

        Header(
            titulo = stringResource(id = R.string.header_ayuda),
            icono = R.drawable.ayudalogo
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(text = stringResource(id = R.string.text_ayuda),
            fontWeight = FontWeight.Bold)

            Text(text = stringResource(id = R.string.text_ayuda1))

            Text(text = stringResource(id = R.string.text_ayuda2))

            Text(text = stringResource(id = R.string.text_ayuda4))

            Text(text = stringResource(id = R.string.text_ayuda5))

            Text(text = stringResource(id = R.string.text_ayuda6))

            Image(
                painter = painterResource(id = R.drawable.buscaminas),
                contentDescription = stringResource(id = R.string.ph_buscaminas),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .padding(bottom = 40.dp)
            )

            Button(onClick = onVolver) {
                Text(stringResource(id = R.string.on_volver))
            }
        }
    }
}

data class CfgPartida(
    val alias: String,
    val filas: Int,
    val columnas: Int,
    val porcentajeMinas: Int,
    val tiempoActivo: Boolean
)
@Composable
fun Configuracion(modifier: Modifier = Modifier, onEmpezar: (CfgPartida) -> Unit) {

    var alias by rememberSaveable { mutableStateOf("") }
    var medida by rememberSaveable { mutableIntStateOf(7) }
    var tiempoActivado by rememberSaveable { mutableStateOf(false) }
    var porcentajeMinas by rememberSaveable { mutableIntStateOf(25) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        Header(
            titulo = stringResource(id = R.string.header_config),
            icono = R.drawable.icono_mina
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {

            Spacer(modifier = Modifier.height(15.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.alias),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(stringResource(id = R.string.label_alias))
            }

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = alias,
                onValueChange = { alias = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.graella),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(stringResource(id = R.string.graella))
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                listOf(7, 6, 5).forEach { value ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 10.dp)
                    ) {
                        RadioButton(
                            selected = medida == value,
                            onClick = { medida = value }
                        )
                        Text(text = value.toString())
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column {
                Text(stringResource(id = R.string.control_tiempo))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.tiempo),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )

                    Checkbox(
                        checked = tiempoActivado,
                        onCheckedChange = { tiempoActivado = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.icono_mina),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(stringResource(id = R.string.porc_minas))
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                listOf(15, 25, 35).forEach { value ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 10.dp)
                    ) {
                        RadioButton(
                            selected = porcentajeMinas == value,
                            onClick = { porcentajeMinas = value }
                        )
                        Text(text = value.toString())
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {
                    onEmpezar(
                        CfgPartida(
                            alias = alias,
                            filas = medida,
                            columnas = medida,
                            porcentajeMinas = porcentajeMinas,
                            tiempoActivo = tiempoActivado
                        )
                    )
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(id = R.string.boton_empezar2))
            }
        }
    }
}
@Composable
fun Juego(modifier: Modifier = Modifier, config: CfgPartida) {

    val tablero = remember(config) {
        List(config.filas) {
            MutableList(config.columnas) {
                CasillaEstado()
            }
        }
    }

    val casillasDescubiertas = tablero.flatten().count { it.descubierta }

    val totalCasillas = config.filas * config.columnas
    val casillasRestantes = totalCasillas - casillasDescubiertas

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        Header(
            titulo = "Partida en marcha",
            icono = R.drawable.icono_mina
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text = "$casillasRestantes casillas")

            if (config.tiempoActivo) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.tiempo),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "10s")
                }
            }
        }

        Tablero(tablero)
    }
}
class CasillaEstado {
    var descubierta by mutableStateOf(false)
    var esMina by mutableStateOf(false)
}

@Composable
fun Tablero(tablero: List<List<CasillaEstado>>) {

    Column(modifier = Modifier.padding(10.dp)) {

        tablero.forEach { fila ->
            Row {
                fila.forEach { casilla ->
                    Casilla(casilla)
                }
            }
        }
    }
}

@Composable
fun Casilla(estado: CasillaEstado) {

    Box(
        modifier = Modifier
            .padding(2.dp)
            .size(50.dp)
            .background(
                if (estado.descubierta)
                    colorResource(id = R.color.white)
                else
                    colorResource(id = R.color.purple_700)
            )
            .clickable(enabled = !estado.descubierta) {
                estado.descubierta = true
            },
        contentAlignment = Alignment.Center
    ) {

    }
}

@Composable
fun Header(titulo: String, icono: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(id = R.color.verde_header1),
                        colorResource(id = R.color.verde_header2),
                        colorResource(id = R.color.verde_header3),
                    )
                )
            )
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically //El Texto
    ) {

        Image(
            painter = painterResource(id = icono),
            contentDescription = stringResource(id = R.string.icono_header),
            modifier = Modifier.size(45.dp)
        )

        Text(
            text = titulo,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 10.dp)


        )
    }
}



@Preview(showBackground = true)
@Composable
fun PrincipalPreview() {
    BuscaminasTheme {
        Principal(onIrAyuda = {},onEmpezarpartida = {})
    }
}

@Preview(showBackground = true)
@Composable
fun AyudaPreview() {
    BuscaminasTheme {
        Ayuda(onVolver = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ConfiguracionPreview() {
    BuscaminasTheme {
        Configuracion(onEmpezar = {})
    }
}

@Preview(showBackground = true)
@Composable
fun JuegoPreview() {
    BuscaminasTheme {
        Juego(
            config = CfgPartida(
                alias = "Albert",
                filas = 5,
                columnas = 5,
                porcentajeMinas = 25,
                tiempoActivo = true
            )
        )
    }
}