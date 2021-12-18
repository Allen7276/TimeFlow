package com.aseane.timeflow

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * 改变时间的格式 会触动ViewModel的变化
 */
//val calendar: Calendar = Calendar.getInstance()
fun changeCalendarFormat(viewModel: MainViewModel) {
    if (viewModel.timeFormat.value == null || viewModel.timeFormat.value == MainViewModel.TimeFormat.Base24) {
        viewModel.editTimeFormat(MainViewModel.TimeFormat.Base12)
    }
    else if (viewModel.timeFormat.value == MainViewModel.TimeFormat.Base12) {
        viewModel.editTimeFormat(MainViewModel.TimeFormat.Base24)
    }
}

fun getCurrentTime(viewModel: MainViewModel): List<Int> {
    val calendar: Calendar = Calendar.getInstance()
    val timeList = ArrayList<Int>().apply {
        if (viewModel.timeFormat.value == MainViewModel.TimeFormat.Base24) add(0, calendar.get(Calendar.HOUR_OF_DAY))
        else {
            // 这里的判断是因为当检测到中午十二点的时候 Java在十二进制环境下显示为 00PM 与我们正常对中午12点判断时间的体验不一样
            // 直接将12加上去
            if (calendar.get(Calendar.HOUR_OF_DAY) == 12) add(0, 12)
            else add(0, calendar.get(Calendar.HOUR))
        }
        add(1, calendar.get(Calendar.MINUTE))
    }
    return timeList
}

/**
 * 刷新时间 update Time
 * 会触动ViewModel的变化
 */
fun updateTime(viewModel: MainViewModel) {
    val hourLeft: Int = if(getCurrentTime(viewModel)[0]<10) 0 else getCurrentTime(viewModel)[0].toString()[0].digitToInt()
    val hourRight: Int = if(getCurrentTime(viewModel)[0]<10) getCurrentTime(viewModel)[0] else getCurrentTime(viewModel)[0].toString()[1].digitToInt()
    val minuteLeft: Int = if(getCurrentTime(viewModel)[1]<10) 0 else getCurrentTime(viewModel)[1].toString()[0].digitToInt()
    val minuteRight: Int = if(getCurrentTime(viewModel)[1]<10) getCurrentTime(viewModel)[1] else getCurrentTime(viewModel)[1].toString()[1].digitToInt()
    assert(hourLeft<10 && hourRight<10 && minuteLeft<10 && minuteRight<10)
    assert(hourLeft>=0 && hourRight>=0 && minuteLeft>=0 && minuteRight>=0)
    viewModel.editTopLeft(hourLeft)
    viewModel.editTopRight(hourRight)
    viewModel.editBottomLeft(minuteLeft)
    viewModel.editBottomRight(minuteRight)
}

/**
 * 图片地址哈希表映射 imageHash
 */
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

/**
 * 时间哈希表的映射 timeFormatHash
 */
val timeFormatHash = HashMap<String, Int>().apply {
    put("am", R.drawable.ic_am)
    put("pm", R.drawable.ic_pm)
}