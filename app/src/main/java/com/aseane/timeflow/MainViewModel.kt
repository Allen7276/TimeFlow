package com.aseane.timeflow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private var _topLeftModel = MutableLiveData<Int>().apply {
        value = 1
    }
    val topLeftModel get() = _topLeftModel
    fun editTopLeft(it: Int) {
        if(this._topLeftModel.value != it) _topLeftModel.value = it
    }

    private var _topRightModel = MutableLiveData<Int>().apply {
        value = 1
    }
    val topRightModel get() = _topRightModel
    fun editTopRight(it: Int) {
        if(this._topRightModel.value != it) _topRightModel.value = it
    }

    private var _bottomLeftModel = MutableLiveData<Int>().apply {
        value = 1
    }
    val bottomLeftModel get() = _bottomLeftModel
    fun editBottomLeft(it: Int) {
        if(this._bottomLeftModel.value != it) _bottomLeftModel.value = it
    }

    private var _bottomRightModel = MutableLiveData<Int>().apply {
        value = 1
    }
    val bottomRightModel get() = _bottomRightModel
    fun editBottomRight(it: Int) {
        if(this._bottomRightModel.value != it) _bottomRightModel.value = it
    }
}