package com.apollo.timeflow.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.apollo.timeflow.R
import com.apollo.timeflow.databinding.ItemLineBinding
import com.apollo.timeflow.databinding.ItemSettingsBinding
import com.apollo.timeflow.ui.uiState.SettingUIState

class SettingAdapter(
    private val settingList: List<SettingUIState>,
) : RecyclerView.Adapter<SettingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder =
        when (viewType) {
            R.layout.item_settings ->
                SettingViewHolder.ItemSettingViewHolder(
                    ItemSettingsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                )

            R.layout.item_line ->
                SettingViewHolder.ItemLineViewHolder(
                    ItemLineBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                )

            else -> throw Exception("unknown view type $viewType")
        }


    override fun getItemCount(): Int = settingList.size

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        when (holder) {
            is SettingViewHolder.ItemSettingViewHolder -> {
                val item = settingList[position] as? SettingUIState.ItemSettingUIState ?: return
                val mContext = holder.binding.root.context ?: return
                holder.binding.name.text = ContextCompat.getString(mContext, item.nameRes)
            }

            is SettingViewHolder.ItemLineViewHolder -> {
                ViewCompat.setBackground(holder.binding.vBg, ContextCompat.getDrawable(
                    holder.binding.root.context,
                    R.color.white
                ))
            }
        }
    }

    override fun getItemViewType(position: Int): Int = settingList[position].itemLayout
}