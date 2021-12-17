package com.aseane.timeflow

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * 获取当前东八区的时间 GMT+8:00
 */
fun getCurrentTime(): List<Int> {
    val calendar = Calendar.getInstance()
    calendar.timeZone = TimeZone.getTimeZone("GMT+8:00")

    val timeList = ArrayList<Int>().apply {
        add(0, calendar.get(Calendar.HOUR))
        add(1, calendar.get(Calendar.MINUTE))
    }
    return timeList
}

/**
 * 刷新时间 update Time
 */
fun updateTime(viewModel: MainViewModel) {
    val hourLeft: Int = if(getCurrentTime()[0]<10) 0 else getCurrentTime()[0].toString()[0].digitToInt()
    val hourRight: Int = if(getCurrentTime()[0]<10) getCurrentTime()[0] else getCurrentTime()[0].toString()[1].digitToInt()
    val minuteLeft: Int = if(getCurrentTime()[1]<10) 0 else getCurrentTime()[1].toString()[0].digitToInt()
    val minuteRight: Int = if(getCurrentTime()[1]<10) getCurrentTime()[1] else getCurrentTime()[1].toString()[1].digitToInt()
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

