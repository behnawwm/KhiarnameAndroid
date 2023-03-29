package com.example.khiarname

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.khiarname.data.Portal
import com.example.khiarname.ui.theme.KhiarnameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KhiarnameTheme {

                val viewModel by viewModels<MainViewModel>()
                val state by viewModel.state.collectAsState()

                Column(modifier = Modifier.fillMaxSize()) {
                    TelepantingScreen(
                        currentStep = state.currentStep,
                        stepCount = state.stepCount,
                        portals = state.portals,
                        modifier = Modifier.weight(1f)
                    )
                    AnimatedVisibility(visible = state.currentStep == state.stepCount - 1) {
                        Text(
                            "End Reached!🎉",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                viewModel.goToPreviousStep()
                            },
                            modifier = Modifier.weight(1f),
                            enabled = state.currentStep != 0
                        ) {
                            Text("previous step")
                        }

                        Button(
                            onClick = {
                                viewModel.goToNextStep()
                            },
                            modifier = Modifier.weight(1f),
                            enabled = state.currentStep != state.stepCount - 1
                        ) {
                            Text("next step")
                        }
                    }
                }

            }
        }
    }


}
