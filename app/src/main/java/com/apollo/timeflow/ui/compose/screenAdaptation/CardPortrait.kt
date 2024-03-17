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

@Preview(
    widthDp = 411,
    heightDp = 872,
)
@Composable
fun CardLargePortrait(
    deviceTypes: Device = Device.Phone(),
    leftOnClick: () -> Unit = {},
    rightOnClick: () -> Unit = {},
    isShowTimeFormat: State<Boolean?> = mutableStateOf(true),
    currentTimeFormat: State<String?> = mutableStateOf("AM"),
    isShowDateFormat: State<Boolean?> = mutableStateOf(true),
    currentDateFormat: State<String?> = mutableStateOf("02.01.2021"),
    hourLeftNumber: State<Int> = mutableIntStateOf(0),
    hourRightNumber: State<Int> = mutableIntStateOf(9),
    minuteLeftNumber: State<Int> = mutableIntStateOf(1),
    minuteRightNumber: State<Int> = mutableIntStateOf(5),
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
                isShowTimeFormat = isShowTimeFormat,
                currentTimeFormat = currentTimeFormat,
                leftNumber = hourLeftNumber,
                rightNumber = hourRightNumber,
            )
        }
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            TimeCard(
                deviceTypes = deviceTypes,
                clickable = rightOnClick,
                isShowTimeFormat = remember { mutableStateOf(false) },
                currentTimeFormat = currentTimeFormat,
                leftNumber = minuteLeftNumber,
                rightNumber = minuteRightNumber,
            )
        }

        if (isShowDateFormat.value == true) {
            Box(contentAlignment = Alignment.BottomCenter) {
                Text(
                    currentDateFormat.value ?: "",
                    color = Color.White,
                    fontSize = getFontSize(deviceTypes),
                    modifier = Modifier.padding(bottom = 10.dp),
                    fontFamily = defaultFontFamily,
                )
            }
        }
    }
}