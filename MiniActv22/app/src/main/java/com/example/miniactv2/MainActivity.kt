package com.example.miniactv2

import android.R.attr.text
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miniactv2.ui.theme.MiniActv2Theme
import java.util.Locale
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniActv2Theme {
                MyApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyApp() {
    val currentLanguage = Locale.getDefault().language
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.current_language, currentLanguage),
                fontWeight = FontWeight.Bold
            )

            Greeting(
                name = stringResource(id = R.string.android_name),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 25.dp)
            )

            Pib(
                modifier = Modifier,
            )

            Button(
                onClick = { },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 25.dp)
            ) {
                Text(
                    text = stringResource(R.string.calcul_pib)
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val orientation =
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE)
            stringResource(R.string.landscape) else stringResource(R.string.portrait)
    Text(
        text = stringResource(R.string.hello , orientation),
        modifier = modifier
    )
}

@Composable
fun Pib(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.pib_original),
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(top = 30.dp)

    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MiniActv2Theme {
        Greeting("Android")
    }
}

