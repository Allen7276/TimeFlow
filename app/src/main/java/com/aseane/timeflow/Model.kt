package com.aseane.timeflow

import java.util.*
import kotlin.collections.ArrayList

/**
 * ViewModel层的数据变化
 * 获取时间的方式 Model 与ViewModel进行绑定
 */
object Model {
    /**
     * MVVM对应的Model层 获取数据
     */
    private fun getCurrentTime(viewModel: MainViewModel): List<Int> {
        val calendar: Calendar = Calendar.getInstance()
        val timeList = ArrayList<Int>().apply {
            if (viewModel.timeFormat.value == MainViewModel.TimeFormat.Base24) add(0, calendar.get(
                Calendar.HOUR_OF_DAY))
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
     * 刷新时间 updateTime
     * 会触动ViewModel的变化
     * 如果要使用MVVM架构对这里进行划分 这里属于ViewModel层 解析数据并且发布订阅
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
     * 改变时间的格式 会触动ViewModel的变化
     * 如果要使用MVVM架构对这里进行划分 这里属于ViewModel层 解析数据并且发布订阅
     */
    fun changeCalendarFormat(viewModel: MainViewModel) {
        if (viewModel.timeFormat.value == null || viewModel.timeFormat.value == MainViewModel.TimeFormat.Base24) {
            viewModel.editTimeFormat(MainViewModel.TimeFormat.Base12)
        }
        else if (viewModel.timeFormat.value == MainViewModel.TimeFormat.Base12) {
            viewModel.editTimeFormat(MainViewModel.TimeFormat.Base24)
        }
    }
}