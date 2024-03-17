package com.apollo.timeflow

import android.app.Activity
import android.util.DisplayMetrics
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.pow
import kotlin.math.sqrt

val imageHash = HashMap<Int, Int>().apply {
    put(0, R.drawable.ic_number0)
    put(1, R.drawable.ic_number1)
    put(2, R.drawable.ic_number2)
    put(3, R.drawable.ic_number3)
    put(4, R.drawable.ic_number4)
    put(5, R.drawable.ic_number5)
    put(6, R.drawable.ic_number6)
    put(7, R.drawable.ic_number7)
    put(8, R.drawable.ic_number8)
    put(9, R.drawable.ic_number9)
}

val defaultFontFamily = FontFamily(
    fonts = listOf(Font(R.font.poppins_bold, FontWeight.Light))
)

fun getFontSize(deviceType: Device): TextUnit = when (deviceType) {
    is Device.Phone -> 16.sp
    is Device.Tablet -> 32.sp
    is Device.TV -> 32.sp
}

sealed class Device {
    abstract val width: Dp
    abstract val height: Dp

    class Phone(override val width: Dp = 120.dp, override val height: Dp = 300.dp) : Device()
    class Tablet(override val width: Dp = 160.dp, override val height: Dp = 400.dp) : Device()

    class TV(override val width: Dp = 200.dp, override val height: Dp = 500.dp) : Device()
}

@Suppress("DEPRECATION")
fun Activity.getDeviceType(): Device {
    val dm = DisplayMetrics()
    this.windowManager.defaultDisplay?.getMetrics(dm)
    val diagonalPixels =
        sqrt(dm.widthPixels.toDouble().pow(2.0) + dm.heightPixels.toDouble().pow(2.0))
    val screenSize = diagonalPixels / (160 * dm.density)
    return when {
        (screenSize < 8.0f) -> Device.Phone()
        (screenSize >= 8.0f && screenSize < 15.0f) -> Device.Tablet()
        else -> Device.TV()
    }
}