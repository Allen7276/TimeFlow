package com.aseane.timeflow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val model: Model = Model.getInstance()

    /**
     * # 时间格式
     */
    private var _timeFormat = MutableLiveData<TimeFormat>().apply { value = TimeFormat.Base12 }
    val timeFormat: LiveData<TimeFormat> get() = _timeFormat
    private fun editTimeFormat(it: TimeFormat) {
        if (this._timeFormat.value?.baseSystem != it.baseSystem) _timeFormat.value = it
    }

    /**
     * # 左上时间数字
     */
    private var _topLeftModel = MutableLiveData<Int>()
    val topLeftModel: LiveData<Int> get() = _topLeftModel
    private fun editTopLeft(it: Int) {
        if (this._topLeftModel.value != it) _topLeftModel.value = it
    }

    /**
     * # 右上时间数字
     */
    private var _topRightModel = MutableLiveData<Int>()
    val topRightModel: LiveData<Int> get() = _topRightModel
    private fun editTopRight(it: Int) {
        if (this._topRightModel.value != it) _topRightModel.value = it
    }

    /**
     * # 左下时间数字
     */
    private var _bottomLeftModel = MutableLiveData<Int>()
    val bottomLeftModel: LiveData<Int> get() = _bottomLeftModel
    private fun editBottomLeft(it: Int) {
        if (this._bottomLeftModel.value != it) _bottomLeftModel.value = it
    }

    /**
     * # 右下时间数字
     */
    private var _bottomRightModel = MutableLiveData<Int>()
    val bottomRightModel: LiveData<Int> get() = _bottomRightModel
    private fun editBottomRight(it: Int) {
        if (this._bottomRightModel.value != it) _bottomRightModel.value = it
    }

    /**
     * 改变时间的格式 会触动ViewModel的变化
     * 如果要使用MVVM架构对这里进行划分 这里属于ViewModel层 解析数据并且发布订阅
     */
    fun changeCalendarFormat() {
        if (_timeFormat.value == null || _timeFormat.value == TimeFormat.Base24) {
            editTimeFormat(TimeFormat.Base12)
        } else if (_timeFormat.value == TimeFormat.Base12) {
            editTimeFormat(TimeFormat.Base24)
        }
    }

    /**
     * 刷新时间 updateTime
     * 会触动ViewModel的变化
     * 如果要使用MVVM架构对这里进行划分 这里属于ViewModel层 解析数据并且发布订阅
     */
    fun updateTime() {
        val hourLeft: Int =
            if (model.getCurrentTime(_timeFormat.value!!)[0] < 10) 0 else model.getCurrentTime(
                _timeFormat.value!!)[0].toString()[0].digitToInt()
        val hourRight: Int =
            if (model.getCurrentTime(_timeFormat.value!!)[0] < 10) model.getCurrentTime(_timeFormat.value!!)[0] else model.getCurrentTime(
                _timeFormat.value!!
            )[0].toString()[1].digitToInt()
        val minuteLeft: Int =
            if (model.getCurrentTime(_timeFormat.value!!)[1] < 10) 0 else model.getCurrentTime(
                _timeFormat.value!!)[1].toString()[0].digitToInt()
        val minuteRight: Int =
            if (model.getCurrentTime(_timeFormat.value!!)[1] < 10) model.getCurrentTime(_timeFormat.value!!)[1] else model.getCurrentTime(
                _timeFormat.value!!
            )[1].toString()[1].digitToInt()
        assert(hourLeft < 10 && hourRight < 10 && minuteLeft < 10 && minuteRight < 10)
        assert(hourLeft >= 0 && hourRight >= 0 && minuteLeft >= 0 && minuteRight >= 0)
        editTopLeft(hourLeft)
        editTopRight(hourRight)
        editBottomLeft(minuteLeft)
        editBottomRight(minuteRight)
    }

    /**
     * 当前时间的获取
     */
    private val _currentDate = MutableLiveData<String>()
    val currentDate: LiveData<String> = _currentDate
    /**
     * 更新日期
     */
    fun updateDate() {
        val date = model.getCurrentDate()
        _currentDate.value = date
    }

    enum class TimeFormat(val baseSystem: Int) {
        Base12(12),
        Base24(24)
    }
}