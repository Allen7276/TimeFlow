package com.aseane.timeflow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    init {
        updateTime(this)
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
}