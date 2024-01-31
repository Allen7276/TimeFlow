package com.apollo.timeflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RouteViewModel : ViewModel() {

    private val _navigation = MutableStateFlow(0)
    val navigation: Flow<Int> = _navigation

    fun navigateToTimeFragment() {
        viewModelScope.launch {
            _navigation.emit(0)
        }
    }

    fun navigateToSettingFragment() {
        viewModelScope.launch {
            _navigation.emit(1)
        }
    }
}