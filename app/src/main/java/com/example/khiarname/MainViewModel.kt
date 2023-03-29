package com.example.khiarname

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
    )

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()


    fun goToNextStep() {
        _state.update {
            val portalToBeClosed = findOpenPortalOnCurrentStep(
                currentStep = it.currentStep,
                portals = it.portals
            )
            portalToBeClosed?.let { portalToBeClosed ->
                it.copy(
                    currentStep = portalToBeClosed.start,
                    portals = it.portals.toMutableList().apply {
                        remove(portalToBeClosed)
                        add(portalToBeClosed.copy(isOpen = false))
                    }
                )
            } ?: run {
                it.copy(currentStep = it.currentStep + 1)
            }
        }
    }

    fun goToPreviousStep() {
        _state.update {
            val portalToBeOpened = findClosedPortalOnCurrentStep(
                currentStep = it.currentStep,
                portals = it.portals
            )
            portalToBeOpened?.let { portalToBeOpened ->
                it.copy(
                    currentStep = portalToBeOpened.end,
                    portals = it.portals.toMutableList().apply {
                        remove(portalToBeOpened)
                        add(portalToBeOpened.copy(isOpen = true))
                    }
                )
            } ?: run {
                it.copy(currentStep = it.currentStep - 1)
            }
        }
    }

    fun updateStepCount(stepCount: Float) {
        _state.update { it.copy(stepCount = stepCount.roundToInt()) }
    }

    private fun findOpenPortalOnCurrentStep(currentStep: Int, portals: List<Portal>): Portal? {
        portals.filter { it.isOpen }.find { it.end == currentStep }?.let {
            return it
        }

        return null
    }

    private fun findClosedPortalOnCurrentStep(currentStep: Int, portals: List<Portal>): Portal? {
        portals.filter { !it.isOpen }.find { it.start == currentStep }?.let {
            return it
        }

        return null
    }

    fun updatePortals(portalsString: String) {
        _state.update { it.copy(portalsString = portalsString) }
        if (!isPortalsStringValid(portalsString))
            return

        val portals = portalsString
            .replace(" ","")
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
}