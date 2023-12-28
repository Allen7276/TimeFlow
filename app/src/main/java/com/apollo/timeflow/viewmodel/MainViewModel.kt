package com.apollo.timeflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollo.timeflow.model.DataStoreModel
import com.apollo.timeflow.model.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val dataStoreModel: DataStoreModel,
    private val timeModel: Model
) : ViewModel() {

    /**
     * # 时间格式
     */
    private var _timeFormat = MutableStateFlow(TimeFormat.Base12)
    val timeFormat: Flow<TimeFormat> = _timeFormat
    fun editTimeFormat(it: TimeFormat) {
        viewModelScope.launch {
            _timeFormat.emit(it)
        }
    }

    /**
     * # 左上时间数字
     */
    private var _topLeftModelFlowOf = MutableSharedFlow<Int>()
    val topLeftModelFlowOf = _topLeftModelFlowOf
    private suspend fun editTopLeft(it: Int) = withContext(Dispatchers.IO) {
        _topLeftModelFlowOf.emit(it)
    }

    /**
     * # 右上时间数字
     */
    private var _topRightModelFlowOf = MutableSharedFlow<Int>()
    val topRightModelFlowOf: Flow<Int> = _topRightModelFlowOf
    private suspend fun editTopRight(it: Int) = withContext(Dispatchers.IO) {
        _topRightModelFlowOf.emit(it)
    }

    /**
     * # 左下时间数字
     */
    private var _bottomLeftModelFlowOf = MutableSharedFlow<Int>()
    val bottomLeftModelFlowOf: Flow<Int> = this._bottomLeftModelFlowOf
    private suspend fun editBottomLeft(it: Int) = withContext(Dispatchers.IO) {
        _bottomLeftModelFlowOf.emit(it)
    }

    /**
     * # 右下时间数字
     */
    private var _bottomRightModelFlowOf = MutableSharedFlow<Int>()
    val bottomRightModelFlowOf: Flow<Int> = _bottomRightModelFlowOf
    private suspend fun editBottomRight(it: Int) = withContext(Dispatchers.IO) {
        _bottomRightModelFlowOf.emit(it)
    }

    /**
     * 刷新时间 updateTime
     * 会触动ViewModel的变化
     * 如果要使用MVVM架构对这里进行划分 这里属于ViewModel层 解析数据并且发布订阅
     */
    fun updateTime() {
        viewModelScope.launch(Dispatchers.IO) {
            val hourLeft: Int =
                if (timeModel.getCurrentTime(_timeFormat.value)[0] < 10) 0 else timeModel.getCurrentTime(
                    _timeFormat.value
                )[0].toString()[0].digitToInt()
            val hourRight: Int =
                if (timeModel.getCurrentTime(_timeFormat.value)[0] < 10) timeModel.getCurrentTime(
                    _timeFormat.value
                )[0] else timeModel.getCurrentTime(
                    _timeFormat.value
                )[0].toString()[1].digitToInt()
            val minuteLeft: Int =
                if (timeModel.getCurrentTime(_timeFormat.value)[1] < 10) 0 else timeModel.getCurrentTime(
                    _timeFormat.value
                )[1].toString()[0].digitToInt()
            val minuteRight: Int =
                if (timeModel.getCurrentTime(_timeFormat.value)[1] < 10) timeModel.getCurrentTime(
                    _timeFormat.value
                )[1] else timeModel.getCurrentTime(
                    _timeFormat.value
                )[1].toString()[1].digitToInt()
            assert(hourLeft < 10 && hourRight < 10 && minuteLeft < 10 && minuteRight < 10)
            assert(hourLeft >= 0 && hourRight >= 0 && minuteLeft >= 0 && minuteRight >= 0)
            editTopLeft(hourLeft)
            editTopRight(hourRight)
            editBottomLeft(minuteLeft)
            editBottomRight(minuteRight)
        }
    }

    /**
     * 当前时间的获取
     */
    private val _currentDate = MutableSharedFlow<String>()
    val currentDate: Flow<String> = _currentDate

    /**
     * 更新日期
     */
    fun updateDate() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentDate.emit(timeModel.getCurrentDate())
        }
    }

    enum class TimeFormat {
        Base12,
        Base24
    }

    val isDateShowDataStoreFlow = dataStoreModel.isDateShow
    fun isDateShow(value: Boolean) {
        viewModelScope.launch {
            dataStoreModel.isDateShow(value)
        }
    }

    val timeFormatRecordDataStoreFlow = dataStoreModel.timeFormatRecord
    fun timeFormatRecordUpdate(value: Boolean) {
        viewModelScope.launch {
            dataStoreModel.timeFormat(value)
        }
    }
}