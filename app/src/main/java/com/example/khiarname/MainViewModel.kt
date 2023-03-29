package com.example.khiarname

import android.system.Os.remove
import androidx.compose.ui.input.pointer.PointerEventType.Companion.Move
import androidx.lifecycle.ViewModel
import com.example.khiarname.data.Portal
import com.example.khiarname.util.formatToText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.roundToInt

class MainViewModel : ViewModel() {
    data class State(
        val stepCount: Int = 6,
        val portals: List<Portal> = listOf(
            Portal(start = 2, end = 3, isOpen = true),
            Portal(start = 1, end = 4, isOpen = true)
        ),
        val portalsString: String = portals.formatToText(),
        val currentStep: Int = 0,
        val previousMoves: List<Move> = emptyList()
    )

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()


    fun goToNextStep() {
        _state.update {
            val portalOnCurrentStep = findPortalOnCurrentStep(
                currentStep = it.currentStep,
                portals = it.portals
            )
            portalOnCurrentStep?.let { portalOnCurrentStep ->
                if (portalOnCurrentStep.isOpen) {
                    val newStep = portalOnCurrentStep.start
                    val newList = it.previousMoves.toMutableList().apply {
                        add(Move(it.currentStep, newStep))
                    }
                    it.copy(
                        currentStep = newStep,
                        portals = it.portals.toMutableList().apply {
                            remove(portalOnCurrentStep)
                            add(portalOnCurrentStep.copy(isOpen = false))
                        },
                        previousMoves = newList
                    )
                } else {
                    val newStep = it.currentStep + 1
                    val newList = it.previousMoves.toMutableList().apply {
                        add(Move(it.currentStep, newStep))
                    }
                    it.copy(
                        currentStep = newStep,
                        portals = it.portals.toMutableList().apply {
                            remove(portalOnCurrentStep)
                            add(portalOnCurrentStep.copy(isOpen = true))
                        },
                        previousMoves = newList
                    )
                }
            } ?: run {
                val newStep = it.currentStep + 1
                val newList = it.previousMoves.toMutableList().apply {
                    add(Move(it.currentStep, newStep))
                }
                it.copy(
                    currentStep = newStep,
                    previousMoves = newList
                )
            }
        }
    }

    fun goToPreviousStep() {
        _state.update {
            it.previousMoves.lastOrNull()?.let { prevMove ->
                val foundPortal = if (prevMove.to < prevMove.from)
                    it.portals.find { it.start == prevMove.to }
//                else if(prevMove)
//                    it.portals.find { it.start == prevMove.from }
                else
                    null
                it.copy(
                    currentStep = prevMove.from,
                    previousMoves = it.previousMoves.toMutableList().apply { removeLast() },
                    portals = it.portals.toMutableList().apply {
                        foundPortal?.let { foundPortal ->
                            remove(foundPortal)
                            add(foundPortal.copy(isOpen = !foundPortal.isOpen))
                            //todo bug: when portal state changes after passing (close -> open)
                        }
                    }
                )
            } ?: it.copy(
                currentStep = 0
            )
        }
    }

    fun updateStepCount(stepCount: Float) {
        _state.update { it.copy(stepCount = stepCount.roundToInt()) }
    }

    private fun findPortalOnCurrentStep(currentStep: Int, portals: List<Portal>): Portal? {
        portals.find { it.end == currentStep }?.let {
            return it
        }

        return null
    }

    fun updatePortals(portalsString: String) {
        _state.update { it.copy(portalsString = portalsString) }
        if (!isPortalsStringValid(portalsString))
            return

        val portals = portalsString
            .replace(" ", "")
            .split("-")
            .map { it.split(",") }
            .map {
                Portal(
                    start = it[0].toInt(),
                    end = it[1].toInt(),
                    isOpen = true
                )
            }

        _state.update { it.copy(portals = portals) }

    }

    private fun isPortalsStringValid(portalsString: String): Boolean {
        val regex = """\d+,\d+( - \d+,\d+)*""".toRegex()
        return regex.matches(portalsString)
    }

    data class Move(
        val from: Int,
        val to: Int
    )
}