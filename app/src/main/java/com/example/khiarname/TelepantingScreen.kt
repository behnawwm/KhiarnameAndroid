package com.example.khiarname

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.khiarname.PortalState.*

@Composable
fun TelepantingScreen(
    currentStep: Int,
    stepCount: Int,
    portals: List<Portal>,
    onPortalUsed: (Portal) -> Unit,
    modifier: Modifier = Modifier,
) {

    BoxWithConstraints(modifier = modifier) {
        val maxWidthSize = maxWidth
        val itemWidth = maxWidthSize / stepCount

        val antOffset by animateDpAsState(targetValue = currentStep * itemWidth + itemWidth / 2)

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                (0..stepCount).forEach { index ->
                    IndexItem(
                        index = index,
                        width = itemWidth,
                        state = providePortalState(index, portals)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .offset(x = antOffset)
                    .size(16.dp)
                    .background(Color.Red)
            )

        }
    }
}

fun providePortalState(
    index: Int,
    portals: List<Portal>
): PortalState? { //todo what if start of multiple portals are the same
    portals.find { it.start == index }?.let {
        return Start(end = it.end)
    }
    portals.find { it.end == index }?.let {
        return EndOpen(start = it.start)
    }
    return null
}

//fun providePortalState(index: Int, portals: List<Portal>): PortalState? {
////    return when (index) {
////        in portals.map { it.end } -> EndOpen()
////        in portals.map { it.start } -> Start()
////        else -> null
////    }
//}

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
                            is EndClose -> Color.DarkGray
                            is EndOpen -> Color.Cyan
                            is Start -> Color.LightGray
                        }
                    )
            ) {
                Text(
                    text = when (state) {
                        is EndClose -> "start: ${state.start}"
                        is EndOpen -> "start: ${state.start}"
                        is Start -> "end: ${state.end}"
                    }
                )
            }
        }

    }
}


//@Preview
//@Composable
//private fun TelepantingScreenPreview() {
//    TelepantingScreen(modifier = Modifier.fillMaxSize())
//}