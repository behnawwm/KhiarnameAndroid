package com.example.khiarname

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@Composable
fun TelepantingScreen(
    currentStep: Int,
    stepCount: Int,
    portals: List<Portal>,
    modifier: Modifier = Modifier,
) {

    BoxWithConstraints(modifier = modifier) {
        val maxWidthSize = maxWidth
        val itemWidth = maxWidthSize / stepCount

        val antOffset by animateDpAsState(targetValue = currentStep * itemWidth + itemWidth / 2)

        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                (0..stepCount).forEach { index ->
                    IndexItem(
                        index = index,
                        width = itemWidth
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

@Composable
fun IndexItem(index: Int, width: Dp, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(width)
            .height(100.dp)
    ) {
        Text(
            index.toString(),
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}


//@Preview
//@Composable
//private fun TelepantingScreenPreview() {
//    TelepantingScreen(modifier = Modifier.fillMaxSize())
//}