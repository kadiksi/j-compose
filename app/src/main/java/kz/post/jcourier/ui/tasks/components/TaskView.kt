package kz.post.jcourier.ui.tasks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kz.post.jcourier.R
import kz.post.jcourier.data.model.task.Task

@Composable
fun TaskAddress(task: Task) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.LightGray,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            task.addressFrom?.address?.let {
                TextView(
                    stringResource(R.string.from_address, it),
                    bottomPaddign = 8.dp
                )
            }
            task.addressTo?.address?.let {
                TextView(
                    stringResource(R.string.to_address, it),
                    topPaddign = 8.dp
                )
            }
        }
    }
}

@Composable
fun ViewDivider() {
    Divider(
        modifier = Modifier
            .size(16.dp),
        color = Color.White
    )
}

@Composable
fun TaskSenderView(task: Task) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.LightGray,
                // rounded corner to match with the OutlinedTextField
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            task.contactFrom?.name?.let {
                TextView(
                    stringResource(R.string.from_contact, it),
                )
            }
            task.contactTo?.name?.let {
                TextView(
                    stringResource(R.string.to_contact, it),
                )
            }
        }
    }
}