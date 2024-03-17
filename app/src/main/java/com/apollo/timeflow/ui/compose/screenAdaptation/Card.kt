package com.apollo.timeflow.ui.compose.screenAdaptation

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import com.apollo.timeflow.Device
import com.apollo.timeflow.viewmodel.MainViewModel


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
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    val isPortrait = (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT)
    if (isPortrait) {
        CardLargePortrait(
            deviceTypes,
            leftOnClick,
            rightOnClick,
            viewModel,
        )
    } else {
        CardLargeLandSpace(
            deviceTypes,
            leftOnClick,
            rightOnClick,
            viewModel,
        )
    }
}