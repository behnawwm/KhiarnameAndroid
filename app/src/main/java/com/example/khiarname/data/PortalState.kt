package com.example.khiarname.data

sealed interface PortalState {
    class Start(val end: Int) : PortalState
    class EndOpen(val start: Int) : PortalState
    class EndClose(val start: Int) : PortalState
}