package com.example.khiarname

import android.os.Bundle
import android.util.Half.toFloat
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.khiarname.data.Portal
import com.example.khiarname.ui.theme.KhiarnameTheme
import com.example.khiarname.util.formatToText

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
                    Text("moves: ${state.previousMoves.map { Pair(it.from, it.to) }}")
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .border(1.dp, Color.Black)
                            .padding(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("step count: ${state.stepCount}")
                            Spacer(modifier = Modifier.width(8.dp))
                            Slider(
                                value = state.stepCount.toFloat(),
                                onValueChange = {
                                    viewModel.updateStepCount(it)
                                },
                                valueRange = 1f..20f,
                                steps = 20
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                        ) {
                            Text("portals: ${state.portals.formatToText()}")
                            Spacer(modifier = Modifier.height(8.dp))
                            TextField(
                                value = state.portalsString,
                                onValueChange = {
                                    viewModel.updatePortals(it)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                label = {
                                    Text("Enter portal pairs separated by - ")
                                }
                            )
                        }
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


