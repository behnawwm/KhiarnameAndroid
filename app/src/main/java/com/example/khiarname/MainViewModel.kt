package com.example.khiarname

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.khiarname.data.Portal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {
    data class State(
        val stepCount: Int = 6,
        val portals: List<Portal> = listOf(
            Portal(start = 2, end = 3, isOpen = true),
            Portal(start = 1, end = 4, isOpen = true)
        ),
        val currentStep: Int = 0,
    )

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()


    fun goToNextStep() {
        _state.update {
            it.copy(
                currentStep = findNextStep(
                    currentStep = _state.value.currentStep,
                    portals = _state.value.portals,
                )
            )
        }
    }

    fun goToPreviousStep() {
        _state.update {
            it.copy(
                currentStep = findPreviousStep(
                    currentStep = _state.value.currentStep,
                    portals = _state.value.portals,
                    onPortalOpen = { openedPortal ->
                        _state.update {
                            it.copy(portals = _state.value.portals.toMutableList() + openedPortal)
                        }
                    }
                )
            )
        }
    }

    private fun findNextStep(
        currentStep: Int,
        portals: List<Portal>,
    ): Int {
        portals.filter { it.isOpen }.find { it.end == currentStep }?.let {
            closePortal(it)
            return it.start
        }

        return currentStep + 1
    }

    private fun closePortal(portal: Portal) {
        _state.update {
            val currentPortals = _state.value.portals.toMutableList().apply {
                remove(portal)
                add(portal.copy(isOpen = false))
            }
            it.copy(portals = currentPortals)
        }
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