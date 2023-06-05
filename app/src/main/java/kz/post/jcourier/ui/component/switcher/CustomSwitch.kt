@file:OptIn(ExperimentalMaterialApi::class)

package kz.post.jcourier.ui.component.switcher

import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check

@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onCheckedChange(!checked) }
            .padding(8.dp)
            .background(color = if (checked) Color.Green else Color.Red)
            .height(48.dp)
            .width(96.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.White, CircleShape)
//                .align(Alignment.Center)
        ) {
            if (checked) {
                Icon(
                    modifier = Modifier.align(Alignment.Center),
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Checked",
                    tint = Color.Green
                )
            }
        }
    }
}