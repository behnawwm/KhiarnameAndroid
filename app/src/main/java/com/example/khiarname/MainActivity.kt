package com.example.khiarname

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
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

                val stepCount: Int = 6
                val portals = remember {
                    mutableStateListOf(
                        Portal(start = 2, end = 3, isOpen = true),
                        Portal(start = 1, end = 4, isOpen = true)
                    )
                }

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
                    AnimatedVisibility(visible = currentStep == stepCount - 1) {
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
                                currentStep = findPreviousStep(
                                    currentStep = currentStep,
                                    portals = portals,
                                    onPortalOpen = {
                                        portals.remove(it)
                                        portals.add(it.copy(isOpen = true))
                                    }
                                )
                            },
                            modifier = Modifier.weight(1f),
                            enabled = currentStep != 0
                        ) {
                            Text("previous step")
                        }

                        Button(
                            onClick = {
                                currentStep = findNextStep(
                                    currentStep = currentStep,
                                    portals = portals,
                                    onPortalClose = {
                                        portals.remove(it)
                                        portals.add(it.copy(isOpen = false))
                                    }
                                )
                            },
                            modifier = Modifier.weight(1f),
                            enabled = currentStep != stepCount - 1
                        ) {
                            Text("next step")
                        }
                    }
                }

            }
        }
    }

    private fun findNextStep(
        currentStep: Int,
        portals: List<Portal>,
        onPortalClose: (Portal) -> Unit,
    ): Int {
        portals.filter { it.isOpen }.find { it.end == currentStep }?.let {
            onPortalClose(it)
            return it.start
        }

        return currentStep + 1
    }

    private fun findPreviousStep(
        currentStep: Int,
        portals: List<Portal>,
        onPortalOpen: (Portal) -> Unit,
    ): Int {
        portals.filter { !it.isOpen }.find { it.start == currentStep }?.let {
            onPortalOpen(it)
            return it.end
        }

        return currentStep - 1
    }
}
