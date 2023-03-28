package com.example.khiarname

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IndexItem(
    index: Int,
    width: Dp,
    state: PortalState?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(width)
            .height(100.dp)
    ) {
        Text(
            index.toString(),
            modifier = Modifier.align(Alignment.TopCenter)
        )
        state?.let { state ->
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .align(Alignment.Center)
                    .background(
                        when (state) {
                            is PortalState.EndClose -> Color.DarkGray
                            is PortalState.EndOpen -> Color.Cyan
                            is PortalState.Start -> Color.LightGray
                        }
                    )
                    .padding(vertical = 2.dp, horizontal = 6.dp)
            ) {
                Text(
                    text = when (state) {
                        is PortalState.EndClose -> "start: ${state.start}"
                        is PortalState.EndOpen -> "start: ${state.start}"
                        is PortalState.Start -> "end: ${state.end}"
                    }
                )
            }
        }

    }
}
