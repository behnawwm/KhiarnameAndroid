package com.example.khiarname

sealed interface PortalState {
    class Start(val end: Int) : PortalState
    class EndOpen(val start: Int) : PortalState
    class EndClose(val start: Int) : PortalState
}