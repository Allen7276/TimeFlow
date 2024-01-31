package com.apollo.timeflow.ui.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.apollo.timeflow.databinding.ItemLineBinding
import com.apollo.timeflow.databinding.ItemSettingsBinding

sealed class SettingViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    class ItemSettingViewHolder(val binding: ItemSettingsBinding) : SettingViewHolder(binding)
    class ItemLineViewHolder(val binding: ItemLineBinding): SettingViewHolder(binding)
}