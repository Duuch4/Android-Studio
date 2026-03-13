package com.example.miniactv3

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miniactv3.ui.theme.MiniActv3Theme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val byeMessage = intent.getStringExtra("bye_message") ?: ""
        val repetitions = intent.getStringExtra("repetitions") ?: "0"
        val repetitionsInt = repetitions.toIntOrNull() ?: 0
        val finalMessage = byeMessage.repeat(repetitionsInt)

        setContent {
            MiniActv3Theme {
                ByeScreen(finalMessage)
            }
        }
    }
}

@Composable
fun ByeScreen(finalMessage: String) {
    val activity = LocalActivity.current

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(text = finalMessage)

            Button(
                onClick = {
                    activity?.finish()
                },
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text("Go back")
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MiniActv3Theme {
        Greeting2("Android")
    }
}