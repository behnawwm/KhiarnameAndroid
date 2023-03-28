package com.example.khiarname

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.khiarname.ui.theme.KhiarnameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KhiarnameTheme {

                val stepCount: Int = 4
                val portals: List<Portal> = listOf(
                    Portal(2, 1),
//            Portal(4, 2)
                )

                var currentStep by remember {
                    mutableStateOf(0)
                }

                Column(modifier = Modifier.fillMaxSize()) {
                    TelepantingScreen(
                        currentStep = currentStep,
                        stepCount = stepCount,
                        portals = portals,
                        modifier = Modifier.weight(1f)
                    )
                    Button(onClick = { currentStep += 1 }) {
                        Text("next step")
                    }
                }

            }
        }
    }
}
