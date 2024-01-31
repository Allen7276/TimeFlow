package com.apollo.timeflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollo.timeflow.service.TimeFormatRecordDataStoreService
import com.apollo.timeflow.service.TimeDataService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TimeDataViewModel(
    private val timeFormatRecordService: TimeFormatRecordDataStoreService,
    private val timeDataService: TimeDataService
) : ViewModel() {

    private var _timeFormat = MutableStateFlow(TimeFormat.Base12)
    val timeFormat: Flow<TimeFormat> = _timeFormat
    fun editTimeFormat(it: TimeFormat) {
        viewModelScope.launch {
            _timeFormat.emit(it)
        }
    }

    private var _topLeftModelFlowOf = MutableSharedFlow<Int>()
    val topLeftModelFlowOf = _topLeftModelFlowOf
    private suspend fun editTopLeft(it: Int) = withContext(Dispatchers.IO) {
        _topLeftModelFlowOf.emit(it)
    }

    private var _topRightModelFlowOf = MutableSharedFlow<Int>()
    val topRightModelFlowOf: Flow<Int> = _topRightModelFlowOf
    private suspend fun editTopRight(it: Int) = withContext(Dispatchers.IO) {
        _topRightModelFlowOf.emit(it)
    }

    private var _bottomLeftModelFlowOf = MutableSharedFlow<Int>()
    val bottomLeftModelFlowOf: Flow<Int> = this._bottomLeftModelFlowOf
    private suspend fun editBottomLeft(it: Int) = withContext(Dispatchers.IO) {
        _bottomLeftModelFlowOf.emit(it)
    }

    private var _bottomRightModelFlowOf = MutableSharedFlow<Int>()
    val bottomRightModelFlowOf: Flow<Int> = _bottomRightModelFlowOf
    private suspend fun editBottomRight(it: Int) = withContext(Dispatchers.IO) {
        _bottomRightModelFlowOf.emit(it)
    }

    fun updateTime() {
        viewModelScope.launch(Dispatchers.IO) {
            val hourLeft: Int =
                if (timeDataService.getCurrentTime(_timeFormat.value)[0] < 10) 0 else timeDataService.getCurrentTime(
                    _timeFormat.value
                )[0].toString()[0].digitToInt()
            val hourRight: Int =
                if (timeDataService.getCurrentTime(_timeFormat.value)[0] < 10) timeDataService.getCurrentTime(
                    _timeFormat.value
                )[0] else timeDataService.getCurrentTime(
                    _timeFormat.value
                )[0].toString()[1].digitToInt()
            val minuteLeft: Int =
                if (timeDataService.getCurrentTime(_timeFormat.value)[1] < 10) 0 else timeDataService.getCurrentTime(
                    _timeFormat.value
                )[1].toString()[0].digitToInt()
            val minuteRight: Int =
                if (timeDataService.getCurrentTime(_timeFormat.value)[1] < 10) timeDataService.getCurrentTime(
                    _timeFormat.value
                )[1] else timeDataService.getCurrentTime(
                    _timeFormat.value
                )[1].toString()[1].digitToInt()
            editTopLeft(hourLeft)
            editTopRight(hourRight)
            editBottomLeft(minuteLeft)
            editBottomRight(minuteRight)
        }
    }

    private val _currentDate = MutableSharedFlow<String>()
    val currentDate: Flow<String> = _currentDate

    fun updateDate() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentDate.emit(timeDataService.getCurrentDate())
        }
    }

    enum class TimeFormat {
        Base12,
        Base24
    }

    val isDateShowDataStoreFlow = timeFormatRecordService.isDateShow
    fun isDateShow(value: Boolean) {
        viewModelScope.launch {
            timeFormatRecordService.isDateShow(value)
        }
    }

    val timeFormatRecordDataStoreFlow = timeFormatRecordService.timeFormatRecord
    fun timeFormatRecordUpdate(value: Boolean) {
        viewModelScope.launch {
            timeFormatRecordService.timeFormat(value)
        }
    }
}