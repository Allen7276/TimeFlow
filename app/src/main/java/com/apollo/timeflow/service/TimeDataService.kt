package com.apollo.timeflow.service

import com.apollo.timeflow.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

class TimeDataService private constructor() {
    companion object {
        private var instance: TimeDataService? = null
        fun getInstance(): TimeDataService {
            if (instance == null) instance = TimeDataService()
            return instance!!
        }
    }

    fun getCurrentTime(timeFormat: MainViewModel.TimeFormat): List<Int> {
        val calendar: Calendar = Calendar.getInstance()
        val timeList = ArrayList<Int>().apply {
            if (timeFormat == MainViewModel.TimeFormat.Base24) {
                add(0, calendar.get(Calendar.HOUR_OF_DAY))
            } else {
                if (calendar.get(Calendar.HOUR_OF_DAY) == 12) add(0, 12)
                else add(0, calendar.get(Calendar.HOUR))
            }
            add(1, calendar.get(Calendar.MINUTE))
        }
        return timeList
    }

    fun getCurrentDate(): String {
        val date = Date()
        val simpleDateFormat = SimpleDateFormat("MM.dd.yyyy", Locale.CHINA)
        return simpleDateFormat.format(date)
    }
}