package com.apollo.timeflow.service

import com.apollo.timeflow.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Service 获取数据
 */
class TimeDataService private constructor() {
    companion object {
        private var instance: TimeDataService? = null
        fun getInstance(): TimeDataService {
            if (instance == null) instance = TimeDataService()
            return instance!!
        }
    }

    /**
     * MVVM对应的Model层 获取数据
     */
    fun getCurrentTime(timeFormat: MainViewModel.TimeFormat): List<Int> {
        val calendar: Calendar = Calendar.getInstance()
        val timeList = ArrayList<Int>().apply {
            if (timeFormat == MainViewModel.TimeFormat.Base24) {
                add(0, calendar.get(Calendar.HOUR_OF_DAY))
            } else {
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
     * 生成一段mm/dd/yyyy的[String]时间类型
     */
    fun getCurrentDate(): String {
        val date = Date()
        val simpleDateFormat = SimpleDateFormat("MM.dd.yyyy", Locale.CHINA)
        return simpleDateFormat.format(date)
    }
}