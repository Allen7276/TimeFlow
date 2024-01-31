package com.apollo.timeflow.ui.uiState

import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.apollo.timeflow.R

sealed class SettingUIState(
    @LayoutRes val itemLayout: Int,
) {
    class ItemSettingUIState(
        @StringRes val nameRes: Int,
    ) : SettingUIState(R.layout.item_settings)

    object LineUIState: SettingUIState(R.layout.item_line)
}