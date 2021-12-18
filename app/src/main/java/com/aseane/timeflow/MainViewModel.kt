package com.aseane.timeflow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private var _timeFormat = MutableLiveData<TimeFormat>().apply {
        value = TimeFormat.Base12
    }
    val timeFormat get() = _timeFormat
    fun editTimeFormat(it: TimeFormat) {
        if (this._timeFormat.value?.baseSystem != it.baseSystem) _timeFormat.value = it
    }

    private var _topLeftModel = MutableLiveData<Int>()
    val topLeftModel get() = _topLeftModel
    fun editTopLeft(it: Int) {
        if(this._topLeftModel.value != it) _topLeftModel.value = it
    }

    private var _topRightModel = MutableLiveData<Int>()
    val topRightModel get() = _topRightModel
    fun editTopRight(it: Int) {
        if(this._topRightModel.value != it) _topRightModel.value = it
    }

    private var _bottomLeftModel = MutableLiveData<Int>()
    val bottomLeftModel get() = _bottomLeftModel
    fun editBottomLeft(it: Int) {
        if(this._bottomLeftModel.value != it) _bottomLeftModel.value = it
    }

    private var _bottomRightModel = MutableLiveData<Int>()
    val bottomRightModel get() = _bottomRightModel
    fun editBottomRight(it: Int) {
        if(this._bottomRightModel.value != it) _bottomRightModel.value = it
    }

    enum class TimeFormat(val baseSystem: Int) {
        Base12(12),
        Base24(24)
    }
}