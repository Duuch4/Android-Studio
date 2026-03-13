package com.example.miniactv3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miniactv3.ui.theme.MiniActv3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniActv3Theme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val hello = stringResource(R.string.hello_world)
    var greetingMessage by remember { mutableStateOf(hello) }
    var byeMessage by remember { mutableStateOf("") }
    var repetitions by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Text(text = greetingMessage)

            Text(
                text = stringResource(R.string.bye_message),
                modifier = Modifier.padding(top = 10.dp)
            )

            TextField(
                value = byeMessage,
                onValueChange = { byeMessage = it },
                label = { Text(stringResource(R.string.bye_message_2)) },
                modifier = Modifier.padding(top = 10.dp)
            )

            Text(
                text = stringResource(R.string.repetitions),
                modifier = Modifier.padding(top = 10.dp)
            )

            OutlinedTextField(
                value = repetitions,
                onValueChange = { repetitions = it },
                label = { Text(stringResource(R.string.repetitions_2)) },
                modifier = Modifier.padding(top = 10.dp)
            )

            Button(
                onClick = {
                    val intent = Intent(context, MainActivity2::class.java)

                    intent.putExtra("bye_message", byeMessage)
                    intent.putExtra("repetitions", repetitions)
                    context.startActivity(intent)
                },
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(stringResource(R.string.bye_screen))
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MiniActv3Theme {
        Greeting("Android")
    }
}