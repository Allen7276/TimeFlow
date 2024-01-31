package com.apollo.timeflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollo.timeflow.R
import com.apollo.timeflow.ui.uiState.SettingUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SettingDataViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<SettingUIState>?>(null)
    val items: Flow<List<SettingUIState>?> = _items

    fun fetchSettingItems() {
        viewModelScope.launch(Dispatchers.IO) {
            _items.emit(
                listOf(
                    SettingUIState.ItemSettingUIState(
                        nameRes = R.string.international_setting,
                    ),
                    SettingUIState.LineUIState,
                    SettingUIState.ItemSettingUIState(
                        nameRes = R.string.time_formattion,
                    ),
                    SettingUIState.LineUIState,
                    SettingUIState.ItemSettingUIState(
                        nameRes = R.string.date_formattion,
                    ),
                    SettingUIState.LineUIState,
                    SettingUIState.ItemSettingUIState(
                        nameRes = R.string.date_switch,
                    ),
                    SettingUIState.LineUIState,
                )
            )
        }
    }
}