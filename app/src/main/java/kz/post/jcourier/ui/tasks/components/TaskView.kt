package kz.post.jcourier.ui.tasks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kz.post.jcourier.R
import kz.post.jcourier.data.model.task.Task
import kz.post.jcourier.utils.toDateTimeFormat
import kz.post.jcourier.utils.toTimeFormat

@Composable
fun TaskSenderAddress(task: Task) {
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
            TextViewAnnotated(
                R.string.from_address, task.getAddress(task.addressFrom),
            )
            task.contactFrom?.companyName?.let {
                TextViewAnnotated(
                    R.string.from_company, it,
                )
            }
            task.contactFrom?.name?.let {
                TextView(
                    it,
                    bottomPaddign = 8.dp,
                    topPaddign = 2.dp
                )
            }
            task.addressFrom?.comment?.let {
                TextViewAnnotated(
                    R.string.merchant_comment,
                    it,
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
fun TaskClientView(task: Task) {
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
            TextViewAnnotated(
                R.string.to_address,
                task.getAddress(task.addressTo)
            )
            task.contactTo?.name?.let {
                TextViewAnnotated(
                    R.string.to_contact,
                    it,
                )
            }
            task.contactTo?.dateFrom?.let {
                TextViewAnnotated(
                    R.string.planed_date,
                    toDateTimeFormat(it) + "-" + toTimeFormat(task.contactTo?.dateTo!!)
                )
            }
            task.addressTo?.comment?.let {
                TextViewAnnotated(
                    R.string.customer_comment,
                    it,
                )
            }
        }
    }
}