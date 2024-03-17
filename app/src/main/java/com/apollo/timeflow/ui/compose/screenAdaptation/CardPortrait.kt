package com.apollo.timeflow.ui.compose.screenAdaptation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apollo.timeflow.Device
import com.apollo.timeflow.defaultFontFamily
import com.apollo.timeflow.getFontSize
import com.apollo.timeflow.ui.compose.component.TimeCard
import com.apollo.timeflow.viewmodel.MainViewModel

@Preview(
    widthDp = 411,
    heightDp = 872,
)
@Composable
fun CardLargePortrait(
    deviceTypes: Device = Device.Phone(),
    leftOnClick: () -> Unit = {},
    rightOnClick: () -> Unit = {},
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            TimeCard(
                deviceTypes = deviceTypes,
                clickable = leftOnClick,
                isShowTimeFormat = viewModel.isDateShowDataStoreFlow.collectAsState(initial = true),
                currentTimeFormat = viewModel.amOrPm,
                leftNumber = viewModel.hourLeftNumberState,
                rightNumber = viewModel.hourRightNumberState,
            )
        }
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            TimeCard(
                deviceTypes = deviceTypes,
                clickable = rightOnClick,
                isShowTimeFormat = remember { mutableStateOf(false) },
                currentTimeFormat = null,
                leftNumber = viewModel.minuteLeftNumberState,
                rightNumber = viewModel.minuteRightNumberState,
            )
        }

        if (viewModel.timeFormatRecordDataStoreFlow.collectAsState(initial = false).value) {
            Box(contentAlignment = Alignment.BottomCenter) {
                Text(
                    viewModel.currentDate.collectAsState(initial = "").value,
                    color = Color.White,
                    fontSize = getFontSize(deviceTypes),
                    modifier = Modifier.padding(bottom = 10.dp),
                    fontFamily = defaultFontFamily,
                )
            }
        }
    }
}