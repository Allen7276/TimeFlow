package com.apollo.timeflow.ui.compose.screenAdaptation

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import com.apollo.timeflow.Device


@Preview(
    showBackground = true,
    widthDp = 872,
    heightDp = 411,
)
@Composable
fun Card(
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
    isShowTimeFormat.value ?: return
    isShowDateFormat.value ?: return


    val isPortrait = (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT)
    if (isPortrait) {
        CardLargePortrait(
            deviceTypes,
            leftOnClick,
            rightOnClick,
            isShowTimeFormat,
            currentTimeFormat,
            isShowDateFormat,
            currentDateFormat,
            hourLeftNumber,
            hourRightNumber,
            minuteLeftNumber,
            minuteRightNumber,
        )
    } else {
        CardLargeLandSpace(
            deviceTypes,
            leftOnClick,
            rightOnClick,
            isShowTimeFormat,
            currentTimeFormat,
            isShowDateFormat,
            currentDateFormat,
            hourLeftNumber,
            hourRightNumber,
            minuteLeftNumber,
            minuteRightNumber,
        )
    }
}