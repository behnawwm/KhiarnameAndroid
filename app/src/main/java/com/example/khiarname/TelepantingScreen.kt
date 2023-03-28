package com.example.khiarname

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.khiarname.data.Portal
import com.example.khiarname.data.PortalState

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
            Ant(
                modifier = Modifier.offset(x = antOffset)
            )


        }
    }
}

fun providePortalState(
    index: Int,
    portals: List<Portal>
): PortalState? { //todo what if start of multiple portals are the same
    portals.find { it.start == index }?.let {
        return PortalState.Start(end = it.end)
    }
    portals.find { it.end == index }?.let {
        return if (it.isOpen)
            PortalState.EndOpen(start = it.start)
        else
            PortalState.EndClose(start = it.start)
    }
    return null
}


@Composable
fun Ant(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ant),
        contentDescription = null,
        modifier = modifier
            .size(40.dp)
    )
}

//@Preview
//@Composable
//private fun TelepantingScreenPreview() {
//    TelepantingScreen(modifier = Modifier.fillMaxSize())
//}