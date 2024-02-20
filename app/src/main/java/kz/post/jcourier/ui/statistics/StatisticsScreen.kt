package kz.post.jcourier.ui.statistics

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.datastore.dataStore
import androidx.hilt.navigation.compose.hiltViewModel
import kz.post.jcourier.R
import kz.post.jcourier.ui.component.TopBar
import kz.post.jcourier.viewmodel.StatisticsViewModel
import java.util.Calendar
import java.util.Date

@Composable
fun statisticsArchive(
    openDrawer: () -> Unit,
    statisticsViewModel: StatisticsViewModel = hiltViewModel(),
) {
    val mContext = LocalContext.current

    val mCalendar = Calendar.getInstance()
    val mYear: Int = mCalendar.get(Calendar.YEAR)
    val mMonth: Int = mCalendar.get(Calendar.MONTH)
    val mDay: Int = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()

    val amount = statisticsViewModel.uiState.amount.value
    val dateFrom = remember { statisticsViewModel.uiState.dateFrom }
    val dateTo = remember { statisticsViewModel.uiState.dateTo }

    val fromDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            dateFrom.value = "$mYear-${getValidMonth(mMonth + 1)}-${getValidMonth(mDayOfMonth)}"
            statisticsViewModel.getStats()
        }, mYear, mMonth, mDay
    )

    val toDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            dateTo.value = "$mYear-${getValidMonth(mMonth + 1)}-${getValidMonth(mDayOfMonth)}"
            statisticsViewModel.getStats()
        }, mYear, mMonth, mDay
    )

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = stringResource(id = R.string.statistics),
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Button(onClick = {
                        fromDatePickerDialog.datePicker.maxDate = Date().time
                        fromDatePickerDialog.show()
                    }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)) {
                        Text(
                            text = stringResource(R.string.statistics_date_from, dateFrom.value),
                            color = Color.White
                        )
                    }
                    Divider(
                        modifier = Modifier
                            .width(8.dp)
                    )
                    Button(onClick = {
                        toDatePickerDialog.datePicker.maxDate = Date().time
                        toDatePickerDialog.show()
                    }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)) {
                        Text(
                            text = stringResource(R.string.statistics_date_to, dateTo.value),
                            color = Color.White
                        )
                    }
                }

                if (amount.isNotEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .weight(1f),
                            text = stringResource(R.string.statistics_amount, amount),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}

fun getValidMonth(mMonth: Int): String {
    return if (mMonth >= 10)
        mMonth.toString()
    else
        "0$mMonth"
}
