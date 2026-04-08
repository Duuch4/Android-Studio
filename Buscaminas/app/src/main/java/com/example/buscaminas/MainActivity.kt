package com.example.buscaminas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Principal(
            modifier = Modifier.padding(innerPadding)
        )
    }
}
@Composable
fun Principal(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(onClick = { }) {
            Text(text = stringResource(id = R.string.boton_ayuda))
        }

        ElevatedButton(onClick = { }) {
            Text(text = stringResource(id = R.string.boton_empezar))
        }

        Button(onClick = { }) {
            Text(text = stringResource(id = R.string.boton_salir))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PrincipalPreview() {
    BuscaminasTheme {
        Principal()
    }
}